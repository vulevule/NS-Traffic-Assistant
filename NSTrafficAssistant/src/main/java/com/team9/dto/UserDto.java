package com.team9.dto;

import java.io.Serializable;

public class UserDto implements Serializable{
	
	public UserDto(String name, String personalNo, String username, String password, String email,
			String role) {
		super();
		this.name = name;
		this.personalNo = personalNo;
		this.username = username;
		this.password = password;
		
		this.email = email;
		this.role = role;
	}
	public UserDto(String name, String personalNo, String username, String password, String password1, String email,
			String role, AddressDto address) {
		super();
		this.name = name;
		this.personalNo = personalNo;
		this.username = username;
		this.password = password;
		
		this.email = email;
		this.role = role;
		this.address = address;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String personalNo;
	private String username;
	private String password;
	
	
	private String email;
	private String role;
	private AddressDto address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPersonalNo() {
		return personalNo;
	}
	public void setPersonalNo(String personalNo) {
		this.personalNo = personalNo;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public AddressDto getAddress() {
		return address;
	}
	public void setAddress(AddressDto address) {
		this.address = address;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public UserDto() {
		super();
	}
	
	

}
