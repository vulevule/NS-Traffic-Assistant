package com.team9.dto;

public class AddressDto {

	private String street;
	private String city;
	private Integer zip;
	
	public AddressDto() {}
	
	public AddressDto(String street, String city, Integer zip) {
		super();
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getZip() {
		return zip;
	}
	public void setZip(Integer zip) {
		this.zip = zip;
	}
	
	
}
