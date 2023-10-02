package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Usuario;
import com.cibertec.api.repository.UsuarioRepository;
import com.cibertec.api.service.UsuarioService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	@Transactional
	public Usuario guardar(Usuario model) {
		return usuarioRepository.save(model);
	}

	@Override
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@Override
	@Transactional
	public Usuario eliminar(int id) {
		return null;
	}

	@Override
	public List<Usuario> listarPorId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario buscarPorId(int id) {
		return usuarioRepository.findById(id).get();
	}

}
