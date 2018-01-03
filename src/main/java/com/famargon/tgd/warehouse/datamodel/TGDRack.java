package com.famargon.tgd.warehouse.datamodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="racks")
public class TGDRack implements Serializable{

	private static final long serialVersionUID = 584826533L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)    
	private Long id;
	
	private Long roomId;
	private Integer height;
	private String corridorId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getCorridorId() {
		return corridorId;
	}
	public void setCorridorId(String corridorId) {
		this.corridorId = corridorId;
	}
	
}
