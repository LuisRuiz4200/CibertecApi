package com.cibertec.api.serviceImpl;

import java.util.List;
//import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Persona;
import com.cibertec.api.repository.PersonaRepository;
import com.cibertec.api.service.PersonaService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonaServiceImpl implements PersonaService{
	
	private PersonaRepository repo;
	//Listado normal
	@Override
	public List<Persona> listarPersona() {
		
		return repo.findAll();
	}
	//Listado de manera logica
	/*
	 * @Override public List<PersonaM> listarPersona() { return
	 * repo.findAll().stream() .filter(persona -> !persona.isActivo())
	 * .collect(Collectors.toList()); }
	 */
	
	
}
