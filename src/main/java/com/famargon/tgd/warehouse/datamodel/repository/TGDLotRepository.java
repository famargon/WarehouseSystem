package com.famargon.tgd.warehouse.datamodel.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.famargon.tgd.warehouse.datamodel.TGDLot;

@RepositoryRestResource(path="lots",exported=true)
public interface TGDLotRepository extends CrudRepository<TGDLot, Long>{

}
