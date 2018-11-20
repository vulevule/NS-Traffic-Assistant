package com.team9.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Authority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	String name;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Authority() {
	}

	public Authority(Long id, String name, User userAuthority) {
		this();
		this.id = id;
		this.name = name;
		this.user = userAuthority;
	}
	
	

	public Authority(String name, User u) {
		this();
		this.name = name;
		this.user = u;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public User getUserAuthority() {
		return user;
	}

	public void setUserAuthority(User userAuthority) {
		this.user = userAuthority;
	}

}
