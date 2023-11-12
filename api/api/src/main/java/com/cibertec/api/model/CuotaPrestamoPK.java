package com.cibertec.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CuotaPrestamoPK {
	@Column(name="idPrestamo")
	private int idPrestamo;
	@Column(name="idCuotaprestamo")
	private int idCuotaPrestamo;
}
