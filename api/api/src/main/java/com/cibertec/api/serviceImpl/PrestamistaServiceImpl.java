package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.repository.PrestamistaRepository;
import com.cibertec.api.service.PrestamistaService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class PrestamistaServiceImpl implements PrestamistaService {

	private PrestamistaRepository prestamistaRepository ;
	
	@Override
	public Prestamista guardar(Prestamista model) {
		// TODO Auto-generated method stub
		return prestamistaRepository.save(model);
	}

	@Override
	public List<Prestamista> listar() {
		
		return prestamistaRepository.findAll();
	}

	@Override
	public Prestamista eliminar(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Prestamista> listarPorId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Prestamista buscarPorId() {
		// TODO Auto-generated method stub
		return null;
	}

}
