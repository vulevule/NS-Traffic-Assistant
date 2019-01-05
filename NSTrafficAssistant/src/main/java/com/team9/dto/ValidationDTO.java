package com.team9.dto;

public class ValidationDTO {
	
	private String username;

	private String ticketType;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public ValidationDTO(String username,  String ticketType) {
		super();
		this.username = username;
		
		this.ticketType = ticketType;
	}
	public ValidationDTO() {
		super();
	}
	
	

}
