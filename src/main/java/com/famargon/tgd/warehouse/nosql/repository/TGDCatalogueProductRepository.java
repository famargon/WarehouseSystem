package com.famargon.tgd.warehouse.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.famargon.tgd.warehouse.nosql.datamodel.TGDCatalogueProduct;

@RepositoryRestResource(path="catalogueproducts")
public interface TGDCatalogueProductRepository extends MongoRepository<TGDCatalogueProduct, String>{

}
