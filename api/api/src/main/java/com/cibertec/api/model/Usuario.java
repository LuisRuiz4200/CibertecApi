package com.cibertec.api.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_usuario")
public class Usuario {
	@Id
	@Column(name="idUsuario")
	private int idUsuario;
	
	private int idPersona;
	
	private String nombreUsuario;
	private String claveUsuario;
	
	@ManyToOne
	@JoinColumn(name="idRol")
	private Rol rol;
	
	private Date fechaRegistro;
	private Date fechaEdicion;
	
	private int activo;
}
