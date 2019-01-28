package com.team9.dto;

import java.io.Serializable;

public class EditDto implements Serializable{
	
	
	
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String username;
	private String password;
	private String email;
	private AddressDto address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public AddressDto getAddress() {
		return address;
	}
	public void setAddress(AddressDto address) {
		this.address=address;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public EditDto() {
		super();
	}
	
	

}