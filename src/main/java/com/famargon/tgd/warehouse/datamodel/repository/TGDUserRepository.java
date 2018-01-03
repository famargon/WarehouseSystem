package com.famargon.tgd.warehouse.datamodel.repository;

import org.springframework.data.repository.CrudRepository;

import com.famargon.tgd.warehouse.datamodel.TGDUser;

public interface TGDUserRepository extends CrudRepository<TGDUser,Long>{

	public TGDUser findByUsername(String username);
	
}
