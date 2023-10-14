package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Persona;
import com.cibertec.api.repository.PersonaRepository;
import com.cibertec.api.service.personaService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class personaServiceImpl implements personaService{
	
	private PersonaRepository repo;
	//Listado normal
	@Override
	public List<Persona> listarPersona() {
		
		return repo.findAll();
	}
	//Listado de manera logica
	/*
	 * @Override public List<Persona> listarPersona() { return
	 * repo.findAll().stream() .filter(persona -> !persona.isActivo())
	 * .collect(Collectors.toList()); }
	 */
	
	
}
