package com.famargon.tgd.warehouse.nosql.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.famargon.tgd.warehouse.nosql.datamodel.TGDHistoricLot;

@RepositoryRestResource(path="historiclot")
public interface TGDHistoricLotRepository extends CrudRepository<TGDHistoricLot, String> {

}
