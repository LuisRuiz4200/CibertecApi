package com.cibertec.api.model;

import lombok.Data;

@Data
public class TokenSunat {

	private String access_token= "";
	private String token_type="";
	private String expires_in = "";
	
}
