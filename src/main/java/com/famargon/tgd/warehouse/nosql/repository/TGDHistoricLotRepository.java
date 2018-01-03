package com.famargon.tgd.warehouse.nosql.repository;

import org.springframework.data.repository.CrudRepository;

import com.famargon.tgd.warehouse.nosql.datamodel.TGDHistoricLot;

public interface TGDHistoricLotRepository extends CrudRepository<TGDHistoricLot, String> {

}
