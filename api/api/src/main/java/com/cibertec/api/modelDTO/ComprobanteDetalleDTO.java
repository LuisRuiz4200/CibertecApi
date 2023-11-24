package com.cibertec.api.modelDTO;

import lombok.Data;

@Data
public class ComprobanteDetalleDTO {
	
	private int idComprobante;
	private int idComprobanteDetalle;
	
	private int codItem;
	private String descripcion;
	private int cantidadItem;
	private double montoItem;
	private double montoTotal;
}
