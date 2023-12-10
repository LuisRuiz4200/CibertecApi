package com.cibertec.api.model;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_persona")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Persona implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="idpersona")
	private int idPersona;
	//Anotacion para validar que no sea vacio @NotEmpty
//@NotEmpty(message = "Debe ingresar el nombre de la persona")
	private String nombres;
//@NotEmpty(message = "Debe ingresar el apellido de la persona")
	private String apellidos;
//@NotEmpty(message = "Debe ingresar la dirección de la persona")
	private String direccion;
//formato @Email
	//@NotEmpty(message = "Debe ingresar el email de la persona")
	//@Email(message = "La dirección email no es válida")
	private String email;
	//@NotEmpty(message = "Debe ingresar el DNI de la persona")
	private String dni;
	//@NotEmpty(message = "Debe ingresar el RUC de la persona")
	private String ruc;
	@Temporal(TemporalType.DATE)
	@Column(name="fecharegistro")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaRegistro;
	@Temporal(TemporalType.DATE)
	@Column(name="fechaedicion")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaEdicion;
	private boolean activo;
	
	//métodos útiles
	public String getNombresApellidos() {
		return this.nombres + " " + this.apellidos;
	}
	
} //fin de Persona
