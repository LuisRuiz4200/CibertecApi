package com.cibertec.api.service;

import java.util.List;

import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.reuzable.CrudService;

public interface SolicitudPrestamoService extends CrudService<SolicitudPrestamo> {
	
	public List<SolicitudPrestamo> listarPorPrestatario(int idPrestatario);
	public List<SolicitudPrestamo> listarPorPrestamista(int idPrestamista);
	
}
