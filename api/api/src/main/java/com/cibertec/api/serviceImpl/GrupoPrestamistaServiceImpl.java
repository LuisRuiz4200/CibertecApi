package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.repository.GrupoPrestamistaRepository;
import com.cibertec.api.service.GrupoPrestamistaService;

@Service
public class GrupoPrestamistaServiceImpl implements GrupoPrestamistaService {
	
	@Autowired
	GrupoPrestamistaRepository grupoPrestamistaRepository;
	
	@Override
	public GrupoPrestamista guardar(GrupoPrestamista model) {
		
		return grupoPrestamistaRepository.save(model);
	}
	
	@Override
	public List<GrupoPrestamista> listar(){
		
		return grupoPrestamistaRepository.findAll();
	}

	@Override
	public GrupoPrestamista eliminar(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GrupoPrestamista> listarPorId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrupoPrestamista buscarPorId(int idGrupo) {
		
		return grupoPrestamistaRepository.findById(idGrupo).get();
	}

}
