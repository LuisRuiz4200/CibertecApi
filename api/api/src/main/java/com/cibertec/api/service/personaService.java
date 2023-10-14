package com.cibertec.api.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.api.model.PersonaM;


public interface personaService {
	//Creamos metodos para CRUD	
	
		public List<PersonaM> listarPersona();
		public Optional<PersonaM> getById(int id);
}
