package com.famargon.tgd.warehouse.datamodel.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.famargon.tgd.warehouse.datamodel.TGDWarehouseRoom;

@RepositoryRestResource(path="warehouserooms")
public interface TGDWarehouseRoomRepository extends CrudRepository<TGDWarehouseRoom,Long>{

}
