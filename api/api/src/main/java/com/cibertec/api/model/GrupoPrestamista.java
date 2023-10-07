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
import jakarta.persistence.MapsId;
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
	@Column(name = "idgrupo")
	@JsonIgnore
	private int idGrupoprestamista;
	@ManyToOne
	@JoinColumn(name = "idgrupo")
	@MapsId
	private Grupo grupo;
	@ManyToOne
	@JoinColumn(name = "idprestamista")
	@MapsId
	private Prestamista prestamista;
	private boolean activo;
	

}
