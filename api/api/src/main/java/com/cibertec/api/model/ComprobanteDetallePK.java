package com.cibertec.api.model;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ComprobanteDetallePK {

	private int idComprobante;
	private int idComprobanteDetalle;
}
