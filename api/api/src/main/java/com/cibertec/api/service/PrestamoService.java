package com.cibertec.api.service;

import com.cibertec.api.model.Prestamo;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.reuzable.CrudService;

public interface PrestamoService extends CrudService<Prestamo> {
    Prestamo getBySolicitud(SolicitudPrestamo solicitudPrestamo);
}
