package com.cibertec.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="tb_rol")
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int idRol;
	private String descripcion;
	
	@JsonIgnore
	@OneToMany(mappedBy="rol")
	@ToString.Exclude
	private List<Usuario> listaUsuarios;
	
	
	@JsonIgnore
	@OneToMany(mappedBy="rol")
	@ToString.Exclude
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
