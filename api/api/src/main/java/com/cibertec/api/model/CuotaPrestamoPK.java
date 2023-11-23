package com.cibertec.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
public class CuotaPrestamoPK {

	@ManyToOne
	@JoinColumn(name="idPrestamo")
	private Prestamo prestamo;
	private int idCuotaPrestamo;
}
