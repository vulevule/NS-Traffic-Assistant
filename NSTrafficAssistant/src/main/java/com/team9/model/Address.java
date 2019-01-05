package com.team9.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String street;
	@Column(nullable = false)
	private String city;
	@Column(nullable = false)
	private Integer zip;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="address")
	private Set<User> users;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="address")
	private Set<Station> stations;

	public Address() {}
	
	public Address(String street, String city, Integer zip, Set<User> users, Set<Station> stations) {
		this();
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.users = users;
		//this.stations = stations;
	}

	
	
	public Address(Long id, String street, String city, Integer zip) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	public Address(Long id, String street, String city, Integer zip, Set<User> users, Set<Station> stations) {
		this();
		this.id = id;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.users = users;
//		this.stations = stations;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
/*
	public Set<Station> getStations() {
		return stations;
	}

	public void setStations(Set<Station> stations) {
		this.stations = stations;
	}
	
	*/
	
	
}
