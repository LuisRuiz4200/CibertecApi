package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Prestatario;
import com.cibertec.api.repository.PrestatarioRepository;
import com.cibertec.api.service.PrestatarioService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrestatarioServiceImpl implements PrestatarioService{

	private PrestatarioRepository prestatarioRepository;

	@Override
	@Transactional
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
	@Transactional
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
		return prestatarioRepository.findById(id).get();
	}


}
