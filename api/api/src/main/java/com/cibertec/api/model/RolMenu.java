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

	public RolMenuPK getPk() {
		return pk;
	}

	public void setPk(RolMenuPK pk) {
		this.pk = pk;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaEdicion() {
		return fechaEdicion;
	}

	public void setFechaEdicion(Date fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	
	
}
