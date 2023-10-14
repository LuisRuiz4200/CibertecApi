package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.repository.PersonaMRepository;
import com.cibertec.api.repository.PrestamistaMRepository;
import com.cibertec.api.service.PrestamistaMService;
import com.cibertec.api.model.Persona;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrestamistaMServiceImpl implements PrestamistaMService {
	
	private PrestamistaMRepository repo;
	private PersonaMRepository personaRepo;
	//Listado normal 
	
	  //@Override public List<PrestamistaM> listarPrestamista()
	  //{ 
		//  return repo.findAll();
	 // }
	 //Listado de manera logica
	  @Override
	  public List<Prestamista> listarPrestamista() {
	      return repo.findAll().stream()
	   .filter(prestamista -> !prestamista.isActivo() && !prestamista.getPrestamista().isActivo())
	   .collect(Collectors.toList());
	  }

	@Override
	public Prestamista listarPrestamistaPorId(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void guardarPrestamista(Prestamista prestamista) {
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
	//Eliminacion fisica
	/*
	 * @Override public void eliminarPrestamista(int id) { //.deleteById(id);
	 * //eliminamos por ID o COD usamos este repo.deleteById(id); }
	 */
	//----------------------Eliminacion Logica
	@Override
	public void eliminarPrestamista(int id) {
	    Prestamista prestamista = repo.findById(id).orElse(null);
	    if (prestamista != null) {
	        prestamista.getPrestamista().setActivo(true);
	        //al  campo prestamista de tipo PrestamistaM lo cambia a true osea de eliminado
	        prestamista.setActivo(true);
	        repo.save(prestamista);
	    }
	}

	@Override
	public Optional<Prestamista> getPrestamistaById(int id) {
		return repo.findById(id);
	}
	
	
	//----------------------
   
}
