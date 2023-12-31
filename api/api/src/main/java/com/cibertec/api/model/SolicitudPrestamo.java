package com.cibertec.api.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_solicitud_prestamo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudPrestamo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idsolicitudprestamo")
	private int idSolicitudPrestamo;

	@ManyToOne
	@JoinColumn(name = "idprestatario")
	private Prestatario prestatario;

	@ManyToOne
	@JoinColumn(name = "idCuenta")
	@JsonIgnore // NO QUITEN EL JSON IGNORE - EL JSON ESTA GENERANDO MUCHA DUPLICIDAD
	private Cuenta cuentaSolicitud;

	private double monto;
	private double interes;
	private int cuotas;
	private String cuentaBancaria;

	@Column(name = "fecharegistro")
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;

	private String estado;
	private boolean activo;

}
