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
	public List<GrupoPrestamista> getGrupoPrestamistaList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GrupoPrestamista> getGrupoPrestamistaByPrestamista(int idPrestamista) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrupoPrestamista saveGrupoPrestamista(GrupoPrestamista grupoPrestamista) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrupoPrestamista getGrupoPrestamistaByGrupoAndPrestamista(int grupoId, int prestamistaId) {
		// TODO Auto-generated method stub
		return null;
	}

}
