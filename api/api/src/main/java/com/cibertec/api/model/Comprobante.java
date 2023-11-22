package com.cibertec.api.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_comprobante")
@Data
public class Comprobante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idComprobante;
	@ManyToOne()
	@JoinColumn(name="idTipoComprobante")
	private TipoComprobante tipoComprobante;
	private String serie;
	private int correlativo;
	private Date fechaEmision;
	private String rucEmisor;
	private String nomEmisor;
	@ManyToOne
	@JoinColumn(name="idTipoDocumento")
	private TipoDocumento tipoDocumento;
	@ManyToOne
	@JoinColumn(name="idPrestatario")
	private Prestatario prestatario;
	@ManyToOne
	@JoinColumn(name="idPrestamo")
	@JoinColumn(name="idCuotaPrestamo")
	private CuotaPrestamo cuotaPrestamo;
	private String serieRef;
	private int correlativoRef;
	private Date fechaRegistro;
	private String estado ;

}
