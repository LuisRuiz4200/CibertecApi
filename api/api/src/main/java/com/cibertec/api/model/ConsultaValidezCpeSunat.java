package com.cibertec.api.model;

import java.lang.reflect.Type;
import java.sql.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class ConsultaValidezCpeSunat {

	
	
	private String numRuc;
	private String codComp;
	private String numeroSerie;
	private String numero;
	private String fechaEmision;
	private String monto;
	
	private String success;
	private String message;
	private DataResponseApiSunat data = new DataResponseApiSunat();
	
	
	
}
