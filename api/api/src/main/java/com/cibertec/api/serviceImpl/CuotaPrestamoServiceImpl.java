package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.CuotaPrestamo;
import com.cibertec.api.model.CuotaPrestamoPK;
import com.cibertec.api.repository.CuotaPrestamoRepository;
import com.cibertec.api.repository.PrestamistaRepository;
import com.cibertec.api.service.CuotaPrestamoService;

@Service
public class CuotaPrestamoServiceImpl implements CuotaPrestamoService{

	@Autowired
	CuotaPrestamoRepository cuotaPrestamoRepository;
	@Autowired
	PrestamistaRepository prestamistaRepository;
	
	@Override
	public CuotaPrestamo guardar(CuotaPrestamo model) {
		// TODO Auto-generated method stub
		return cuotaPrestamoRepository.save(model);
	}

	@Override
	public List<CuotaPrestamo> listar() {
		// TODO Auto-generated method stub
		return cuotaPrestamoRepository.findAll();
	}

	@Override
	public void eliminar(int id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void eliminarCuota(CuotaPrestamoPK cuotaPrestamoPK) {
		cuotaPrestamoRepository.deleteByCuotaPrestamoPk(cuotaPrestamoPK);
	}
	

	@Override
	public List<CuotaPrestamo> listarPorId(int id) {
		// TODO Auto-generated method stub
		return cuotaPrestamoRepository.findByCuotaPrestamoPkIdPrestamo(id);
	}
	

	@Override
	public CuotaPrestamo buscarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CuotaPrestamo buscarPorCuotaPrestamo(CuotaPrestamoPK cuotaPrestamoPK) {
		
		return cuotaPrestamoRepository.findById(cuotaPrestamoPK).get();
	}
	
	
	
	
}
