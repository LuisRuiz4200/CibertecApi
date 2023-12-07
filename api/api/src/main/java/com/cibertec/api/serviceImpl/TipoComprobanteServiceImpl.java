package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.TipoComprobante;
import com.cibertec.api.repository.TipoComprobanteRepository;
import com.cibertec.api.service.TipoComprobanteService;

@Service
public class TipoComprobanteServiceImpl implements TipoComprobanteService {

	@Autowired
	private TipoComprobanteRepository tipoComprobanteRepository;
	
	@Override
	public TipoComprobante guardar(TipoComprobante model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoComprobante> listar() {
		// TODO Auto-generated method stub
		return tipoComprobanteRepository.findAll();
	}

	@Override
	public void eliminar(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TipoComprobante> listarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoComprobante buscarPorId(int id) {
		// TODO Auto-generated method stub
		return tipoComprobanteRepository.findById(id).get();
	}

}
