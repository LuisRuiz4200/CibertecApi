package com.cibertec.api.model;

import java.util.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_rol_menu")
public class RolMenu {

	@EmbeddedId
	private RolMenuPK pk;
	
	@ManyToOne
	@JoinColumn(name="idRol")
	private Rol rol;
	
	@ManyToOne
	@JoinColumn(name="idMenu")
	private Menu menu;
	
	
	
	private Date fechaRegistro;
	private Date fechaEdicion;
	
	private int activo;
}
