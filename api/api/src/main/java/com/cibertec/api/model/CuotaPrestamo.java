package com.cibertec.api.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "tb_cuota_prestamo")
@Data

public class CuotaPrestamo {

	@EmbeddedId
	private CuotaPrestamoPK cuotaPrestamoPk = new CuotaPrestamoPK();

	private double monto;
	private double interes;

	@Column(name = "montototal")
	private double montoTotal;

	@Column(name = "fechapago")
	private Date fechaPago;

	private String estado;

	@ManyToOne
	@JoinColumn(name = "idPrestamo", insertable = false, updatable = false)
	//@JsonManagedReference
	//@JsonIgnore
	private Prestamo prestamo;
	
	@OneToMany(mappedBy = "cuotaPrestamo")
	@JsonIgnore
	private List<Comprobante> listaComprobantes = new ArrayList<>();
}
