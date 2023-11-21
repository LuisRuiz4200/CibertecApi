package com.cibertec.api.model;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
}
