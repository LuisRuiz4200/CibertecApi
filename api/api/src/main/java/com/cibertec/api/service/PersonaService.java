package com.cibertec.api.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.api.model.Persona;


public interface PersonaService {
	//Creamos metodos para CRUD	
	
		public List<Persona> listarPersona();
		public Optional<Persona> getById(int id);
}
