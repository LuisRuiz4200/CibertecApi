package com.cibertec.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_comprobante_detalle")
@Data
public class ComprobanteDetalle {

	@EmbeddedId
	private ComprobanteDetallePK comprobanteDetallePK = new ComprobanteDetallePK();

	@ManyToOne()
	@JoinColumn(name = "idComprobante", insertable = false, updatable = false)
	@JsonIgnore
	private Comprobante comprobante;
	

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="idPrestamo",referencedColumnName = "idPrestamo",nullable = true),
		@JoinColumn(name="idCuotaPrestamo",referencedColumnName = "idCuotaPrestamo",nullable = true)
	})
	private CuotaPrestamo cuotaPrestamo = new CuotaPrestamo();

	private String codItem;
	private String descripcion;
	private int cantidadItem;
	private double montoItem;
	private double montoTotal;

}
