package com.famargon.tgd.warehouse;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import org.springframework.stereotype.Component;

import com.famargon.tgd.warehouse.nosql.datamodel.TGDCatalogueProduct;

public class SystemSamples {

//	@Autowired
//	private MongoTemplate template;
//	
//	@PostConstruct
//	public void sample() {
//
//		TGDCatalogueProduct product = new TGDCatalogueProduct();
//		product.setDescription("Brand new iphone");
//		product.setName("iphone x");
//		product.setReference("rgnfe12");
//		product.setStock(0);
//		product.setUnitPrice(1300d);
//		
//		template.save(product);
//		
//		
//		TGDCatalogueProduct productFound = template.findOne(query(where("id").is(product.getId())), TGDCatalogueProduct.class);
//		
//		product.setImage("img");
//		template.save(product);
//		
//		try {
//			template.save(productFound);
//		}catch(OptimisticLockingFailureException olfe) {
//			System.out.println("Optimistic locking works!!");
//		}
//		
//	}
	
}
