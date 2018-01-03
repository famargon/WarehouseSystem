package com.famargon.tgd.warehouse.nosql.datamodel;

import java.io.Serializable;

public class TGDHistoricLotProduct implements Serializable{
	
	private static final long serialVersionUID = -6838110402498649448L;
	
	private String catalogueProductId;
	private Integer quantity;
	private String productName;
	private Boolean provisioned;

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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Boolean getProvisioned() {
		return provisioned;
	}
	public void setProvisioned(Boolean provisioned) {
		this.provisioned = provisioned;
	}
	
}
