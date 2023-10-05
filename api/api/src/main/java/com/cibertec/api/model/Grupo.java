package com.cibertec.api.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_grupo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grupo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idgrupo")
	private int idGrupo; 
	
	private String descripcion;
	@OneToMany(mappedBy = "grupo")
	@JsonIgnore
	private List<GrupoPrestamista> listaGrupoPrestamista;

}
