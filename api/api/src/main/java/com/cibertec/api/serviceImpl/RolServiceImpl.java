package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Rol;
import com.cibertec.api.repository.rolRepository;
import com.cibertec.api.service.rolService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RolServiceImpl implements rolService {
	
	private rolRepository repo;
	
	@Override
	public List<Rol> listarRol() {
		return repo.findAll();
	}

}
