package com.cibertec.api.serviceImpl;

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
		// TODO Auto-generated method stub
		return solicitudPrestamisteRepository.save(model);
	}

	@Override
	public List<SolicitudPrestamo> listar() {
		// TODO Auto-generated method stub
		return solicitudPrestamisteRepository.findAll();
	}

	@Override
	public void eliminar(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<SolicitudPrestamo> listarPorId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SolicitudPrestamo buscarPorId(int id) {
		// TODO Auto-generated method stub
		return solicitudPrestamisteRepository.findById(id).get();
	}

	@Override
	public List<SolicitudPrestamo> listarPorPrestatario(int idPrestatario) {
		// TODO Auto-generated method stub
		return solicitudPrestamisteRepository.findByPrestatarioIdPrestatario(idPrestatario);
	}

	@Override
	public List<SolicitudPrestamo> listarPorPrestamista(int idPrestamista) {
		// TODO Auto-generated method stub
		return null;
	}

}
