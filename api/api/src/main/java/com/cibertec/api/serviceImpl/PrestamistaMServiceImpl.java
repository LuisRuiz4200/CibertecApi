package com.cibertec.api.serviceImpl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.PrestamistaM;
import com.cibertec.api.repository.PersonaMRepository;
import com.cibertec.api.repository.PrestamistaMRepository;
import com.cibertec.api.service.PrestamistaMService;
import com.cibertec.api.model.PersonaM;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrestamistaMServiceImpl implements PrestamistaMService {
	
	private PrestamistaMRepository repo;
	private PersonaMRepository personaRepo;
	
	@Override
	public List<PrestamistaM> listarPrestamista() {
		return repo.findAll();
	}

	@Override
	public PrestamistaM listarPrestamistaPorId(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void guardarPrestamista(PrestamistaM prestamista) {
		// Obtener el objeto PersonaM del prestamista
		/*
		 * PersonaM persona = prestamista.getPrestamista();
		 * 
		 * // Si la persona ya existe en la base de datos, actualizarla if
		 * (persona.getIdPersona() != 0) { persona =
		 * personaRepo.findById(persona.getIdPersona()).orElse(null); if (persona !=
		 * null) { // Volver a conectar la persona a la sesión de Hibernate persona =
		 * personaRepo.save(persona); prestamista.setPrestamista(persona); } }
		 * 
		 * // Verificar si el objeto PrestamistaM ya existe en la base de datos if
		 * (prestamista.getIdPrestamista() != 0 &&
		 * repo.existsById(prestamista.getIdPrestamista())) { throw new
		 * RuntimeException("El prestamista ya existe en la base de datos"); }
		 */
				repo.save(prestamista);
		
		//esto con la finalidad para poder actualizar da error de esto
				//detached entity passed to persist: com.cibertec.api.model.PersonaM
		
		//repo.save(prestamista);
		
	}

	@Override
	public void eliminarPrestamista(int id) {
		//.deleteById(id);	
		//eliminamos por ID o COD usamos este
		repo.deleteById(id);
	}

}
