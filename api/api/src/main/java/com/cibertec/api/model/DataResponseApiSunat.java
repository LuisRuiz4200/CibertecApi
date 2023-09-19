package com.cibertec.api.model;

import java.lang.reflect.Type;

import lombok.Data;

@Data
public class DataResponseApiSunat implements Type {

	private String estadoCp;
	private String estadoRuc;
	private String condDomiRuc;
	private String[] observaciones = new String[] {};
	
}
