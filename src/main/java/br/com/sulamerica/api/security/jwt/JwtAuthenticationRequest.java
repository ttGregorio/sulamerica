package br.com.sulamerica.api.security.jwt;

import java.io.Serializable;

public class JwtAuthenticationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cpf;

	private String password;

	public JwtAuthenticationRequest() {
		super();
	}

	public JwtAuthenticationRequest(String cpf, String password) {
		this.cpf = cpf;
		this.password = password;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}