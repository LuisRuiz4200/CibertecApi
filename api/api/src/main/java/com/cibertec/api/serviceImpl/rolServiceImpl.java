package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.tbrol;


import com.cibertec.api.repository.rolRepository;
import com.cibertec.api.service.rolService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class rolServiceImpl implements rolService {
	
	private rolRepository repo;
	
	@Override
	public List<tbrol> listarRol() {
		return repo.findAll();
	}

}
