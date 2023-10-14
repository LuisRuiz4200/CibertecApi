package com.cibertec.api.model;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tb_usuario")
/*
 * @AllArgsConstructor
 * 
 * @NoArgsConstructor
 */
  @Getter
 @Setter
  @ToString
public class tbusuario {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	//@Column(name="idusuario")
	private int idUsuario;
	//@Column(name="nombreusuario")
	private String nombreUsuario;
	//@Column(name="claveusuario")
	private String claveUsuario;
	//FORANEAS //Muchos usuarios a 1 rol
	@ManyToOne
	@JoinColumn(name = "idRol")
	//@Column(name="idrol")
	private tbrol rol;
	// Muchos usuarios a 1 persona
	@ManyToOne
	@JoinColumn(name = "idPersona")
	//@Column(name="idpersona")
	private Persona persona;
	//
	@Temporal(TemporalType.DATE)
	//@Column(name="fecharegistro")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaRegistro;
	@Temporal(TemporalType.DATE)
	//@Column(name="fechaedicion")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaEdicion;
	private boolean activo;
	
	
	
}//fin de tbusuario
