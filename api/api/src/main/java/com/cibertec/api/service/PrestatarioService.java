package com.cibertec.api.service;

import java.util.List;
import java.util.Optional;


import com.cibertec.api.model.Prestatario;


public interface PrestatarioService {

	//Creamos metodos para CRUD	
	
	public List<Prestatario> listarPrestatario();
			
	public Prestatario listarPrestatarioPorId(int id);
			
	public Prestatario guardarPrestatario(Prestatario prestatario);
	
	public void eliminarPrestatario(int id);
	
	Optional<Prestatario> getPrestatarioById(int id);	
	
}
