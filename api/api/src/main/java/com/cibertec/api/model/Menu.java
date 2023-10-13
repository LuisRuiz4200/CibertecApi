package com.cibertec.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_menu")
public class Menu {
	@Id
	private int idMenu;

	private String descripcion;
	private String ruta;
	private int activo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "menu")
	private List<RolMenu> listaRolMenu;

	public int getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public List<RolMenu> getListaRolMenu() {
		return listaRolMenu;
	}

	public void setListaRolMenu(List<RolMenu> listaRolMenu) {
		this.listaRolMenu = listaRolMenu;
	}
	
	
}
