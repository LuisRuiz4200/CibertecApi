package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Prestamo;
import com.cibertec.api.repository.PrestamoRepository;
import com.cibertec.api.service.PrestamoService;

@Service
public class PrestamoServiceImpl implements PrestamoService {


	@Autowired
	private PrestamoRepository prestamoRepository;
	
	
	@Override
	public Prestamo guardar(Prestamo model) {
		// TODO Auto-generated method stub
		return prestamoRepository.save(model);
	}

	@Override
	public List<Prestamo> listar() {
		// TODO Auto-generated method stub
		return prestamoRepository.findAll();
	}

	@Override
	public void eliminar(int id) {
		// TODO Auto-generated method stub
		prestamoRepository.deleteById(id);
	}

	@Override
	public List<Prestamo> listarPorId(int id) {
		// TODO Auto-generated method stub
		return prestamoRepository.findById(id).stream().collect(Collectors.toList());
	}

	@Override
	public Prestamo buscarPorId(int id) {
		// TODO Auto-generated method stub
		return prestamoRepository.findById(id).get();
	}

	

}
