package br.com.querydsl.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


public class TokenDTO {
	
	private String token;
	
	@JsonIgnore
	private Date expirationDate;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}


	
}
