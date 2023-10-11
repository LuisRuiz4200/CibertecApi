package com.cibertec.api.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_grupo_prestamista")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GrupoPrestamista implements Serializable{
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
    @JoinColumn(name = "idGrupo")
	private Grupo grupo;

	@ManyToOne
    @JoinColumn(name = "idPrestamista")
	private PrestamistaM prestamista;

	private boolean activo;
}
