package com.cibertec.api.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_comprobante")
@Data
public class Comprobante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idComprobante;

	@ManyToOne()
	@JoinColumn(name = "idTipoComprobante")
	private TipoComprobante tipoComprobante = new TipoComprobante();

	private String serie;
	private int correlativo;
	private Date fechaEmision;
	private String rucEmisor;
	private String nomEmisor;

	@ManyToOne
	@JoinColumn(name = "idPrestamo")
	private Prestamo prestamo = new Prestamo();

	@ManyToOne
	@JoinColumn(name = "idTipoDocumento")
	private TipoDocumento tipoDocumento = new TipoDocumento();

	@ManyToOne
	@JoinColumn(name = "idPrestatario")
	private Prestatario prestatario = new Prestatario();

	private String numDocReceptor;
	private String nomReceptor;
	private String serieRef;
	private int correlativoRef;
	private Date fechaRegistro;
	private String estado;

	@OneToMany(mappedBy = "comprobante")
	private List<ComprobanteDetalle> listaComprobanteDetalle = new ArrayList<>();

}
