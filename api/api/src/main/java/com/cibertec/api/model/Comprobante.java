package com.cibertec.api.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@JoinColumns({
		@JoinColumn(name="idPrestamo",referencedColumnName = "idPrestamo"),
		@JoinColumn(name="idCuotaPrestamo",referencedColumnName = "idCuotaPrestamo")
	})
	private CuotaPrestamo cuotaPrestamo;
	@ManyToOne
	@JoinColumn(name="idTipoDocumento")
	private TipoDocumento tipoDocumento;
	@ManyToOne
	@JoinColumn(name="idPrestatario")
	private Prestatario prestatario;
	private String numDocReceptor;
	private String nomReceptor;
	private String serieRef;
	private int correlativoRef;
	private Date fechaRegistro;
	private String estado ;
	
	@OneToMany(mappedBy = "comprobante")
	private List<ComprobanteDetalle> listaComprobanteDetalle;

}
