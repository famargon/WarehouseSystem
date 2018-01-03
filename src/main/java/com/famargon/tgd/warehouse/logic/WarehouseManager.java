package com.famargon.tgd.warehouse.logic;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.famargon.tgd.warehouse.datamodel.TGDLot;
import com.famargon.tgd.warehouse.datamodel.TGDLotProduct;
import com.famargon.tgd.warehouse.datamodel.TGDUser;
import com.famargon.tgd.warehouse.datamodel.repository.TGDLotProductRepository;
import com.famargon.tgd.warehouse.datamodel.repository.TGDLotRepository;
import com.famargon.tgd.warehouse.datamodel.repository.TGDUserRepository;
import com.famargon.tgd.warehouse.nosql.datamodel.TGDCatalogueProduct;
import com.famargon.tgd.warehouse.nosql.datamodel.TGDHistoricLot;
import com.famargon.tgd.warehouse.nosql.datamodel.TGDHistoricLotProduct;
import com.famargon.tgd.warehouse.nosql.datamodel.TGDProductStock;
import com.famargon.tgd.warehouse.nosql.repository.TGDCatalogueProductRepository;
import com.famargon.tgd.warehouse.nosql.repository.TGDHistoricLotRepository;

@Service
public class WarehouseManager {
	
	public static final String ROLE_WORKER = "ROLE_WORKER";
	public static final String ROLE_SALESMAN = "ROLE_SALESMAN";
	public static final String ROLE_BOSS = "ROLE_BOSS";
	
	public enum LotType{
		INBOUND,
		OUTBOUND;
	}
	
	@Autowired
	private TGDUserRepository userRepository;
	
	@Autowired
	private TGDHistoricLotRepository historicLotRepository;
	
	@Autowired
	private TGDLotRepository lotRepository;
	
	@Autowired
	private TGDCatalogueProductRepository catalogueProductRepository;
	
	@Autowired
	private TGDLotProductRepository lotProductRepository;
	
	@Autowired
	private MongoTemplate template;
	
	@PostConstruct
	public void init() {
		//worker
		TGDUser worker = userRepository.findByUsername("worker");
		if(worker==null) {
			worker = new TGDUser();
			worker.setFullname("Trabajador del almacén");
			worker.setUsername("worker");
			worker.setPwd("worker");
			worker.setRole(ROLE_WORKER);
			userRepository.save(worker);
		}
		//sales
		TGDUser salesman = userRepository.findByUsername("salesman");
		if(salesman==null) {
			salesman = new TGDUser();
			salesman.setFullname("Vendedor");
			salesman.setUsername("salesman");
			salesman.setPwd("salesman");
			salesman.setRole(ROLE_SALESMAN);
			userRepository.save(salesman);
		}
		//boss
		TGDUser boss = userRepository.findByUsername("boss");
		if(boss==null) {
			boss = new TGDUser();
			boss.setFullname("Responsable");
			boss.setUsername("boss");
			boss.setPwd("boss");
			boss.setRole(ROLE_BOSS);
			userRepository.save(boss);
		}
	}
	
	@PreAuthorize("hasRole('"+ROLE_SALESMAN+"')")
	public Long createLot(LotType type, List<TGDLotProduct> products) {
		TGDLot lot = new TGDLot();
		lot.setCreationDate(new Date());
		lot.setType(type.name());
		lotRepository.save(lot);
		for(TGDLotProduct product : products) {
			if(catalogueProductRepository.findOne(product.getCatalogueProductId())==null) {
				throw new ResourceNotFoundException("Catalogue product "+product.getCatalogueProductId()+" not found");
			}
			product.setLotId(lot.getId());
			lotProductRepository.save(product);
		}
		return lot.getId();
	}
	
	@PreAuthorize("hasRole('"+ROLE_BOSS+"')")
	public TGDHistoricLot confirmLot(Long lotId) {
		TGDLot lot = lotRepository.findOne(lotId);
		if(lot==null) {
			throw new ResourceNotFoundException("Lot "+lotId+" not found");
		}
		lot.setConfirmed(true);
		lot.setConfirmedDate(new Date());
		lotRepository.save(lot);
		//
		return processLot(lot);
	}
	
	@PreAuthorize("hasRole('"+ROLE_WORKER+"')")
	public void inboundLotArrival(Long lotId, Map<String,List<Long>> ubications) {
		TGDLot lot = lotRepository.findOne(lotId);
		if(lot==null) {
			throw new ResourceNotFoundException("Lot "+lotId+" not found");
		}
		List<TGDLotProduct> products = lotProductRepository.findByLotId(lot.getId());
		for(TGDLotProduct product : products) {
			processInboundLotProduct(product,ubications.get(product.getCatalogueProductId()));	
		}
		lot.setArrived(true);
		lot.setArrivedDate(new Date());
		lotRepository.save(lot);
	}
	
	@PreAuthorize("hasRole('"+ROLE_WORKER+"')")
	public void updateUbications(String catalogueProductId, List<Long> newUbications) {
		int retries = 3;
		boolean stop = false;
		do {
			TGDProductStock productStock = template.findOne(query(where("catalogueProductId").is(catalogueProductId)), TGDProductStock.class);
			productStock.setUbications(newUbications.toArray(new Long[newUbications.size()]));
			try {
				template.save(productStock);
				stop = true;
			}catch(OptimisticLockingFailureException olfe) {
				retries--;
			}
		}while(!stop && retries>0);
	}
	
	private TGDHistoricLot processLot(TGDLot lot) {
		List<TGDLotProduct> products = lotProductRepository.findByLotId(lot.getId());
		if(lot.getType().equalsIgnoreCase(LotType.OUTBOUND.name())){
			processOutboundLot(products);
		}
		//el stock en los lotes de entrada no se actualiza hasta que no llega el lote
//		else {
//			processInboundLot(products);
//		}
		lot.setProcessed(true);
		lot.setProcessedDate(new Date());
		lotRepository.save(lot);
		return saveHistoricLot(lot, products);
	}

	private TGDHistoricLot saveHistoricLot(TGDLot lot, List<TGDLotProduct> products) {
		TGDHistoricLot hl = new TGDHistoricLot();
		hl.setConfirmed(lot.isConfirmed());
		hl.setConfirmedDate(lot.getConfirmedDate());
		hl.setCreationDate(lot.getCreationDate());
		hl.setProcessed(true);
		hl.setProcessedDate(lot.getProcessedDate());
		hl.setRelationalId(lot.getId());
		hl.setType(lot.getType());
		hl.setProducts(new ArrayList<>());
		for(TGDLotProduct product : products) {
			TGDHistoricLotProduct hp = new TGDHistoricLotProduct();
			hp.setCatalogueProductId(product.getCatalogueProductId());
			hp.setQuantity(product.getQuantity());
			TGDCatalogueProduct catalogueProduct = catalogueProductRepository.findOne(product.getCatalogueProductId());
			if(catalogueProduct!=null) {
				hp.setProductName(catalogueProduct.getName());				
			}
			hp.setProvisioned(product.getProvisioned());
			hl.getProducts().add(hp);
		}
		historicLotRepository.save(hl);
		return hl;
	}
	
//	private void processInboundLot(List<TGDLotProduct> products) {
//		for(TGDLotProduct product : products) {
//			processInboundLotProduct(product);
//			product.setProvisioned(true);
//			lotProductRepository.save(product);
//		}
//	}
	
	private void processOutboundLot(List<TGDLotProduct> products) {
		for(TGDLotProduct product : products) {
			try {
				processOutboundLotProduct(product);
				product.setProvisioned(true);
			}catch(UnavaiableStockLogicException usle) {
				product.setProvisioned(false);
			}
			lotProductRepository.save(product);
		}
	}
	
	private void processOutboundLotProduct(TGDLotProduct product) {
		int retries = 3;
		boolean stop = false;
		do {
			TGDProductStock productStock = template.findOne(query(where("catalogueProductId").is(product.getCatalogueProductId())), TGDProductStock.class);
			productStock = ensureProductStock(productStock, product.getCatalogueProductId());
			if(product.getQuantity()>productStock.getStock()) {
				throw new UnavaiableStockLogicException();
			}
			productStock.setStock(productStock.getStock()-product.getQuantity());
			try {
				template.save(productStock);
				updateCatalogueStock(product.getCatalogueProductId(), productStock.getStock());
				stop = true;
			}catch(OptimisticLockingFailureException olfe) {
				System.out.println("OptimisticLockingException on product "+product.getCatalogueProductId()+" and lotId "+product.getLotId());
				retries--;
			}
		}while(!stop && retries>0);

	}
	
	private void processInboundLotProduct(TGDLotProduct product, List<Long> newUbications) {
		int retries = 3;
		boolean stop = false;
		do {
			TGDProductStock productStock = template.findOne(query(where("catalogueProductId").is(product.getCatalogueProductId())), TGDProductStock.class);
			productStock = ensureProductStock(productStock, product.getCatalogueProductId());
			productStock.setStock(productStock.getStock()+product.getQuantity());
			newUbications.addAll(Arrays.asList(productStock.getUbications()));
			productStock.setUbications(newUbications.toArray(new Long[newUbications.size()]));
			try {
				template.save(productStock);
				updateCatalogueStock(product.getCatalogueProductId(), productStock.getStock());
				stop = true;
			}catch(OptimisticLockingFailureException olfe) {
				System.out.println("OptimisticLockingException on product "+product.getCatalogueProductId()+" and lotId "+product.getLotId());
				retries--;
			}
		}while(!stop && retries>0);
	}
	
	private TGDProductStock ensureProductStock(TGDProductStock stock, String catalogueProductId) {
		if(stock==null) {
			stock = new TGDProductStock();
			stock.setCatalogueProductId(catalogueProductId);
			stock.setStock(0);
			stock.setUbications(new Long[0]);
			template.save(stock);
		}
		return stock;
	}

	private void updateCatalogueStock(String id, int stock) {
		TGDCatalogueProduct product = catalogueProductRepository.findOne(id);
		if(product!=null) {
			product.setStock(stock);
			try {
				catalogueProductRepository.save(product);				
			}catch(OptimisticLockingFailureException olfe) {
				System.out.println("Save catalogue stock "+olfe);
			}
		}
	}
	
}
