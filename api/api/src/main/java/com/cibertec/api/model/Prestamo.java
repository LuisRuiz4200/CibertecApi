package com.cibertec.api.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name="tb_prestamo")
@Data
public class Prestamo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPrestamo;

	@ManyToOne
	@JoinColumn(name="idSolicitudPrestamo")
	@JsonProperty("solicitudPrestamo")
	private SolicitudPrestamo solicitudPrestamo ;

	private double monto;
	private int cuotas;
	private double tea;
	private double tem;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyy-MM-dd hh:mm:ss")
	private Date fechaRegistro;
	
	private boolean activo;
	
	@OneToMany(mappedBy = "prestamo",cascade = CascadeType.PERSIST)
	@JsonBackReference
	//@JsonManagedReference
	private List<CuotaPrestamo> listaCuotaPrestamo = new ArrayList<>();

	@OneToMany(mappedBy = "prestamo")
	@JsonIgnore
	private List<Comprobante> listaComprobantes = new ArrayList<>();
	
}
