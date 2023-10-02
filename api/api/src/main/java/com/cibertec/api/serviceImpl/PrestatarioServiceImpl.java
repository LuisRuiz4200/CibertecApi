package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Prestatario;
import com.cibertec.api.repository.PrestatarioRepository;
import com.cibertec.api.service.PrestatarioService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrestatarioServiceImpl implements PrestatarioService{

	private PrestatarioRepository prestatarioRepository;

	@Override
	public Prestatario guardar(Prestatario model) {
		// TODO Auto-generated method stub
		return prestatarioRepository.save(model);
	}

	@Override
	public List<Prestatario> listar() {
		// TODO Auto-generated method stub
		return prestatarioRepository.findAll();
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
	public Prestatario buscarPorId() {
		// TODO Auto-generated method stub
		return null;
	}


}
