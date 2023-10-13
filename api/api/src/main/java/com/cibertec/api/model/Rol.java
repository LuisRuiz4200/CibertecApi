package com.cibertec.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_rol")
public class Rol {
	@Id
	private int idRol;
	private String descripcion;
	
	@JsonIgnore
	@OneToMany(mappedBy="rol")
	private List<Usuario> listaUsuarios;
	
	
	@JsonIgnore
	@OneToMany(mappedBy="rol")
	private List<RolMenu> listaRolMenu;


	public int getIdRol() {
		return idRol;
	}


	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}


	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}


	public List<RolMenu> getListaRolMenu() {
		return listaRolMenu;
	}


	public void setListaRolMenu(List<RolMenu> listaRolMenu) {
		this.listaRolMenu = listaRolMenu;
	}
	
	
}
