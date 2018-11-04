package com.team9.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String personalNo;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private Role role;
	
	@ManyToOne(optional=false)
	private Address address;
	
	
	public User() {}
	
	public User(String name, String personalNo, String username, String password, String email, Role role,
			Address address) {
		this();
		this.name = name;
		this.personalNo = personalNo;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.address = address;
	}
	public User(Long id, String name, String personalNo, String username, String password, String email, Role role,
			Address address) {
		this();
		this.id = id;
		this.name = name;
		this.personalNo = personalNo;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.address = address;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	

	
}
