package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.repository.PrestamistaRepository;
import com.cibertec.api.repository.PrestatarioRepository;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestatarioService;

@Service
public class PrestatarioServiceImpl implements PrestatarioService{

	@Autowired
	private PrestatarioRepository prestamistaMRepository;

	@Override
	public Prestatario guardar(Prestatario model) {
		// TODO Auto-generated method stub
		return prestamistaMRepository.save(model);
	}

	@Override
	public List<Prestatario> listar() {
		// TODO Auto-generated method stub
		return prestamistaMRepository.findAll();
	}

	@Override
	public Prestatario eliminar(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Prestatario> listarPorId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Prestatario buscarPorId(int id) {
		// TODO Auto-generated method stub
		return prestamistaMRepository.findById(id).get();
	}

}
