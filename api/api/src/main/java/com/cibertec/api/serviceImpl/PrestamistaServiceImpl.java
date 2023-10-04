package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.repository.PrestamistaRepository;
import com.cibertec.api.service.PrestamistaService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Service
public class PrestamistaServiceImpl implements PrestamistaService {

	
	@Autowired
	PrestamistaRepository prestamistaRepository ;
	
	@Override
	public Prestamista guardar(Prestamista model) {

		return prestamistaRepository.save(model);
	}

	@Override
	public List<Prestamista> listar() {
		
		return prestamistaRepository.findAll();
	}

	@Override
	@Transactional
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
	public Prestamista buscarPorId(int id) {
		return prestamistaRepository.findById(id).get();
	}

}
