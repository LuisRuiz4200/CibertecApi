package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.PersonaM;
import com.cibertec.api.repository.PersonaMRepository;
import com.cibertec.api.service.personaService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class personaServiceImpl implements personaService{
	
	private PersonaMRepository repo;
	//Listado normal
	@Override
	public List<PersonaM> listarPersona() {
		
		return repo.findAll();
	}

	@Override
	public Optional<PersonaM> getById(int id) {
		return repo.findById(id);
	}

	//Listado de manera logica
	/*
	 * @Override public List<PersonaM> listarPersona() { return
	 * repo.findAll().stream() .filter(persona -> !persona.isActivo())
	 * .collect(Collectors.toList()); }
	 */
	
	 
	
}
