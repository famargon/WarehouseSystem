package com.famargon.tgd.warehouse.datamodel.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.famargon.tgd.warehouse.datamodel.TGDLotProduct;

@RepositoryRestResource(path="lotproducts",exported=true)
public interface TGDLotProductRepository extends CrudRepository<TGDLotProduct, Long>{

	public List<TGDLotProduct> findByLotId(Long lotId);
	
}
