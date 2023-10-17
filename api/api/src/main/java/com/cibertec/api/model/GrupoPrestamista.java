package com.cibertec.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class GrupoPrestamista{

    @Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int idGrupoPrestamista;

	@ManyToOne
	private Prestamista idJefePrestamista;

	@ManyToOne
	private Prestamista idAsesorPrestamista;

	private boolean activo; 

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuarioRegistro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuarioActualiza;
}
