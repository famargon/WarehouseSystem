package com.famargon.tgd.warehouse.logic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.famargon.tgd.warehouse.datamodel.TGDLotProduct;
import com.famargon.tgd.warehouse.logic.WarehouseManager.LotType;
import com.famargon.tgd.warehouse.nosql.datamodel.TGDHistoricLot;

@RestController
public class WarehouseController {

	@Autowired
	private WarehouseManager warehouse;
	
	@RequestMapping(value="/warehouse/lot/create/{type}",method=RequestMethod.POST)
	public Long createLot(@PathVariable(name="type")String lotType, @RequestBody List<TGDLotProduct> products) {
		return warehouse.createLot(LotType.valueOf(lotType), products);
	}
	
	@RequestMapping(value="/warehouse/lot/confirm/{id}",method=RequestMethod.POST)
	public TGDHistoricLot confirmLot(@PathVariable(name="id") Long lotId) {
		return warehouse.confirmLot(lotId);
	}
	
	@RequestMapping(value="/warehouse/lot/arrival/{id}",method=RequestMethod.POST)
	public void lotArrival(@PathVariable(name="id") Long lotId, @RequestBody Map<String,List<Long>> ubications) {
		warehouse.inboundLotArrival(lotId, ubications);
	}
	
	@RequestMapping(value="/warehouse/product/{id}",method=RequestMethod.POST)
	public void updateUbications(@PathVariable(name="id") String catalogueProductId, @RequestBody List<Long> newUbications) {
		warehouse.updateUbications(catalogueProductId, newUbications);
	}
	
}
