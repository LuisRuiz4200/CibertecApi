package com.cibertec.api.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.repository.SolicitudPrestamisteRepository;
import com.cibertec.api.service.SolicitudPrestamoService;

@Service
public class SolicitudPrestamoServiceImpl implements SolicitudPrestamoService {

	@Autowired
	private SolicitudPrestamisteRepository solicitudPrestamisteRepository;

	@Override
	public SolicitudPrestamo guardar(SolicitudPrestamo model) {
		// return solicitudPrestamisteRepository.save(model);
		SolicitudPrestamo solicitudGuardada = solicitudPrestamisteRepository.save(model);
		// Obtener el ID después de guardar
		int idSolicitud = solicitudGuardada.getIdSolicitudPrestamo();
		// Puedes imprimir el ID o realizar otras acciones con él si es necesario
		System.out.println("ID de la solicitud guardada: " + idSolicitud);

		return solicitudGuardada;
	}

	@Override
	public List<SolicitudPrestamo> listar() {
		return solicitudPrestamisteRepository.findAll();
	}

	@Override
	public void eliminar(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<SolicitudPrestamo> listarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SolicitudPrestamo buscarPorId(int id) {
		return solicitudPrestamisteRepository.findById(id).get();
	}

	@Override
	public List<SolicitudPrestamo> listarPorPrestatario(int idPrestatario) {
		return solicitudPrestamisteRepository.findByPrestatarioIdPrestatario(idPrestatario);
	}

	@Override
	public List<SolicitudPrestamo> listarPorPrestamista(int idPrestamista) {
		return solicitudPrestamisteRepository.findByPrestatarioPrestamistaPrestatarioIdPrestamista(idPrestamista);
	}

	// FILTROOOOOO
	@Override
	public List<SolicitudPrestamo> filtrarSolicitudes(int idPrestamista, Date fechaDesde, Date fechaHasta) {
		List<SolicitudPrestamo> listaSolicitudes = solicitudPrestamisteRepository.findAll();
		List<SolicitudPrestamo> listaFiltrada = new ArrayList<>();
		for (SolicitudPrestamo solicitud : listaSolicitudes) {
			if (solicitud.getPrestatario().getIdPrestatario() == idPrestamista
					&& solicitud.getFechaRegistro().compareTo(fechaDesde) >= 0
					&& solicitud.getFechaRegistro().compareTo(fechaHasta) <= 0) {
				listaFiltrada.add(solicitud);
			}
		}
		return listaFiltrada;
	}

}
