package com.cibertec.api.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_rol_menu")
public class RolMenu {

	private int idRol;
	private int idMenu;
	
	
	
	private Date fechaRegistro;
	private Date fechaEdicion;
	
	private int activo;
}
