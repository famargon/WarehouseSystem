package com.famargon.tgd.warehouse.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//postgres://faempfbebmzwni:e5ae8e8dfe115a3ddcf15bda3b195f06c7f3b002e81d316d37e1d6d4e2a16771@ec2-54-217-205-90.eu-west-1.compute.amazonaws.com:5432/dfjc7rck84kr9c
/**

//local
spring.datasource.url=jdbc:postgresql://localhost:5432/warehouse
spring.datasource.username=developer
spring.datasource.password=developer
spring.datasource.platform=postgresql

spring:
  datasource:
    url: ${JDBC_DATABASE_URL}
    username: ${JDBC_DATABASE_USERNAME}
    password: ${JDBC_DATABASE_PASSWORD}

 */
@Entity
@Table(name="warehouserooms")
public class TGDWarehouseRoom {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)    
	private Long id;
	
	private String roomName;
	private Double size;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Double getSize() {
		return size;
	}
	public void setSize(Double size) {
		this.size = size;
	}
}
