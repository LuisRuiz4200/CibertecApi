package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Persona;
import com.cibertec.api.repository.PersonaRepository;
import com.cibertec.api.service.PersonaService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PersonaServiceImpl implements PersonaService{
	
	@Autowired
	private PersonaRepository repo;
	//Listado normal
	@Override
	public List<Persona> listarPersona() {
		
		return repo.findAll();
	}

	@Override
	public Optional<Persona> getById(int id) {
		return repo.findById(id);
	}

	//Listado de manera logica
	/*
	 * @Override public List<PersonaM> listarPersona() { return
	 * repo.findAll().stream() .filter(persona -> !persona.isActivo())
	 * .collect(Collectors.toList()); }
	 */
	
	 
	
}
