package com.cibertec.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_menu")
public class Menu {
	@Id
	private int idMenu;

	private String descripcion;
	private String ruta;
	private int activo;
}
