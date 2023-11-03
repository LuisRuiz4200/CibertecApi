package com.cibertec.api.serviceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

import org.springframework.stereotype.Service;


//import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Prestatario;


//import com.cibertec.api.repository.PrestamistaRepository;
import com.cibertec.api.repository.PrestatarioRepository;
import com.cibertec.api.service.PrestatarioService;


import java.util.stream.Collectors;
@Service
@AllArgsConstructor

public class PrestatarioServiceImpl implements PrestatarioService{
	
	private PrestatarioRepository repo;
	
	
	@Override
	public List<Prestatario> listarPrestatario() {
		//return repo.findAll();
		return repo.findAll().stream()
			    .filter(prestatario -> prestatario.isActivo() && prestatario.getPrestatario().isActivo())
				.collect(Collectors.toList());
	}

	@Override
	public Prestatario listarPrestatarioPorId(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Prestatario guardarPrestatario(Prestatario prestatario) {
		return repo.save(prestatario);
	}

	@Override
	public void eliminarPrestatario(int id) {
		Prestatario prestatario = repo.findById(id).orElse(null);
		    if (prestatario != null) {
		    	prestatario.getPrestatario().setActivo(false);
		        //al  campo prestamista de tipo PrestamistaM lo cambia a true osea de eliminado
		    	prestatario.setActivo(false);
		        repo.save(prestatario);
		    }
		
	}


	

}
