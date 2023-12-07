package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.ComprobanteDetalle;
import com.cibertec.api.repository.ComprobanteDetalleRepository;
import com.cibertec.api.service.ComprobanteDetalleService;

@Service
public class ComprobanteDetalleServiceImpl implements ComprobanteDetalleService {

	@Autowired
	private ComprobanteDetalleRepository comprobanteDetalleRepository;
	
	@Override
	public ComprobanteDetalle guardar(ComprobanteDetalle model) {
		// TODO Auto-generated method stub
		return comprobanteDetalleRepository.save(model);
	}

	@Override
	public List<ComprobanteDetalle> listar() {
		// TODO Auto-generated method stub
		return comprobanteDetalleRepository.findAll();
	}

	@Override
	public void eliminar(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<ComprobanteDetalle> listarPorId(int id) {
		// TODO Auto-generated method stub
		return comprobanteDetalleRepository.findByComprobanteDetallePKIdComprobante(id);
	}

	@Override
	public ComprobanteDetalle buscarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
