package com.cibertec.api.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CuotaPrestamoPK {

	private int  idPrestamo;
	private int idCuotaPrestamo;
}
