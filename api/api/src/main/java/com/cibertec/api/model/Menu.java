package com.cibertec.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "tb_menu")
@Data
public class Menu {
	@Id
	private int idMenu;

	private String descripcion;
	private String ruta;
	private boolean activo;

	@JsonIgnore
	@OneToMany(mappedBy = "menu")
	@ToString.Exclude
	private List<RolMenu> listaRolMenu;

}
