package com.cibertec.api.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_grupo_prestamista")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class GrupoPrestamista implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idgrupo")
	@JsonIgnore
	private int idGrupo;
	@ManyToOne
	@JoinColumn(name = "idgrupo", referencedColumnName = "idgrupo", insertable = false, updatable = false)
	private Grupo grupo;
	@ManyToOne
	@JoinColumn(name = "idprestamista")
	private Prestamista prestamista;
	private boolean activo;
	

}
