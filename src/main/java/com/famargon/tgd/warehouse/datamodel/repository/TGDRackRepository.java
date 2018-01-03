package com.famargon.tgd.warehouse.datamodel.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.famargon.tgd.warehouse.datamodel.TGDRack;

@RepositoryRestResource(path="racks")
public interface TGDRackRepository extends CrudRepository<TGDRack,Long>{

}
