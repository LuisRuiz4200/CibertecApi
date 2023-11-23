package com.cibertec.api.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_cuota_prestamo")
@Data

public class CuotaPrestamo {
	
	@EmbeddedId
	private CuotaPrestamoPK cuotaPrestamoPk;
	
	private double monto;
	private double interes;
	@Column(name="montototal")
	private double montoTotal;
	@Column(name="fechapago")
	private Date fechaPago;
	private String estado;
}
