package com.cibertec.api.modelDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ComprobanteDetalleDTO {
	
	@JsonIgnore
	private int idComprobante;
	private int idComprobanteDetalle;

	private String codItem;
	private String descripcion;
	private Integer idPrestamo = null;
	private Integer idCuotaPrestamo = null;
	private int cantidadItem;
	private double montoItem;
	private double montoTotal;
}
