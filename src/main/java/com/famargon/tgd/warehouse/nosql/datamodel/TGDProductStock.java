package com.famargon.tgd.warehouse.nosql.datamodel;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo-template.optimistic-locking
spring.data.mongodb.database=warehouse
spring.data.mongodb.uri=mongodb://localhost:27017
 * @author Fabian
 *
 */
@Document
public class TGDProductStock implements Serializable{

	private static final long serialVersionUID = -208244393934446192L;

	@Id 
	private String id;

	@Version 
	private Long version;
	
	private String catalogueProductId;
	private Long[] ubications;
	private Integer stock;
	  	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getCatalogueProductId() {
		return catalogueProductId;
	}
	public void setCatalogueProductId(String catalogueProductId) {
		this.catalogueProductId = catalogueProductId;
	}
	public Long[] getUbications() {
		return ubications;
	}
	public void setUbications(Long[] ubications) {
		this.ubications = ubications;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
}
