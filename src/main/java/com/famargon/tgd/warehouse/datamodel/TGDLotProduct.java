package com.famargon.tgd.warehouse.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lotproducts")
public class TGDLotProduct {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)    
	private Long id;
	
	private Long lotId;
	
	private String catalogueProductId;
	private Integer quantity;
	private Boolean provisioned;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLotId() {
		return lotId;
	}
	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}
	public String getCatalogueProductId() {
		return catalogueProductId;
	}
	public void setCatalogueProductId(String catalogueProductId) {
		this.catalogueProductId = catalogueProductId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Boolean getProvisioned() {
		return provisioned;
	}
	public void setProvisioned(Boolean provisioned) {
		this.provisioned = provisioned;
	}
}
