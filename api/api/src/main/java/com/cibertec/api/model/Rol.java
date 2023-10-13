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
}
