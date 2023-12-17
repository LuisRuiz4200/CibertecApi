
package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.TipoDocumento;
import com.cibertec.api.repository.TipoDocumentoRepository;
import com.cibertec.api.service.TipoDocumentoService;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;

	@Override
	public TipoDocumento guardar(TipoDocumento model) {
		return tipoDocumentoRepository.save(model);
	}

	@Override
	public List<TipoDocumento> listar() {
		return tipoDocumentoRepository.findAll();
	}

	@Override
	public void eliminar(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<TipoDocumento> listarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoDocumento buscarPorId(int id) {
		return tipoDocumentoRepository.findById(id).get();
	}

}
