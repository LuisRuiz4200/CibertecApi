package com.cibertec.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;

@Embeddable
@Data
public class CuotaPrestamoPK {
	@Column(name="idprestamo")
	private int idPrestamo;
	@Column(name="idcuotaprestamo")
	private int idCuotaPrestamo;
}
