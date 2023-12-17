package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Comprobante;
import com.cibertec.api.repository.ComprobanteRepository;
import com.cibertec.api.service.ComprobanteService;

@Service
public class ComprobanteServiceImpl implements ComprobanteService {

	@Autowired
	private ComprobanteRepository comprobanteRepository;

	@Override
	public Comprobante guardar(Comprobante model) {
		return comprobanteRepository.save(model);
	}

	@Override
	public List<Comprobante> listar() {
		return comprobanteRepository.findAll();
	}

	@Override
	public void eliminar(int id) {
		comprobanteRepository.deleteById(id);
	}

	@Override
	public List<Comprobante> listarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comprobante buscarPorId(int id) {
		return comprobanteRepository.findById(id).get();
	}

}
