package com.cibertec.api.service;

import com.cibertec.api.model.CuotaPrestamo;
import com.cibertec.api.model.CuotaPrestamoPK;
import com.cibertec.api.reuzable.CrudService;

public interface CuotaPrestamoService extends CrudService<CuotaPrestamo> {

	void eliminarCuota(CuotaPrestamoPK cuotaPrestamoPK);

	CuotaPrestamo buscarPorCuotaPrestamo(CuotaPrestamoPK cuotaPrestamoPK);

}
