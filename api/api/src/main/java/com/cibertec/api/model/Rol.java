package com.cibertec.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_rol")
@Data
public class Rol {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int idRol;
		private String descripcion;
		
		@OneToMany(mappedBy = "rol")
		@JsonIgnore
		private List<Usuario> listaUsuario;
}
