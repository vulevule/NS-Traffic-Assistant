package com.team9.dto;

import com.team9.model.Address;

public class UpdateProfileDto {
	public UpdateProfileDto(String username, String name, String email, String password, AddressDto address) {
		super();
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
	}
	
	public UpdateProfileDto() {
		super();
	}

	private String username;
	private String name;
	private String email;
	private String password;
	private AddressDto address;
	public String getName() {
		return name;
	}
	public String getUsername() {
		return username;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AddressDto getAddress() {
		return address;
	}
	public void setAddress(AddressDto address) {
		this.address = address;
	} 

}
