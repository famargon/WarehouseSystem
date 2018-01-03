package com.famargon.tgd.warehouse.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
  Usuarios (empleados de la empresa) (postgresql)
	-username
	-pwd
	-fullname
	-role
 * @author Fabian
 *
 */
@Entity
@Table(name="users")
public class TGDUser {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)    
	private Long id;

	private String username;
	private String pwd;
	private String fullname;
	private String role;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
