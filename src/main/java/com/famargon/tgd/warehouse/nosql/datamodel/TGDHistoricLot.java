package com.famargon.tgd.warehouse.nosql.datamodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TGDHistoricLot implements Serializable{

	private static final long serialVersionUID = 1991345883760514778L;

	@Id 
	private String id;
	
	private Long relationalId;
	
	private String type;
	private Date creationDate;
	private boolean processed = false;//por el sistema, despues de procesarlo el responsable
	private Date processedDate;
	private boolean confirmed = false;//por un responsable
	private Date confirmedDate;
	private boolean arrived = false;
	private Date arrivedDate;
	
	private List<TGDHistoricLotProduct> products;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getRelationalId() {
		return relationalId;
	}

	public void setRelationalId(Long relationalId) {
		this.relationalId = relationalId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Date getConfirmedDate() {
		return confirmedDate;
	}

	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}

	public boolean isArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	public Date getArrivedDate() {
		return arrivedDate;
	}

	public void setArrivedDate(Date arrivedDate) {
		this.arrivedDate = arrivedDate;
	}

	public List<TGDHistoricLotProduct> getProducts() {
		return products;
	}

	public void setProducts(List<TGDHistoricLotProduct> products) {
		this.products = products;
	}
}
