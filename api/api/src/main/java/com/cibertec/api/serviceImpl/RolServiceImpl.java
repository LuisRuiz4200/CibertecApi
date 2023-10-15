package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Rol;
import com.cibertec.api.repository.RolRepository;
import com.cibertec.api.service.RolService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RolServiceImpl implements RolService {
	
	private RolRepository repo;
	
	@Override
	public List<Rol> listarRol() {
		return repo.findAll();
	}

}
