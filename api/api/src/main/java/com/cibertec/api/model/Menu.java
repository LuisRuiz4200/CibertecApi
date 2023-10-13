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
}
