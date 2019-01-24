package com.team9.dto;

import java.io.Serializable;

public class LoginUserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;
	private String role;

	public LoginUserDto() {
	}

	public LoginUserDto(String token, String role) {
		this();
		this.token = token;
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
