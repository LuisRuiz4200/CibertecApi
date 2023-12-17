package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.repository.PrestamistaRepository;
import com.cibertec.api.repository.UsuarioRepository;
import com.cibertec.api.service.PrestamistaService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PrestamistaServiceImpl implements PrestamistaService {

	@Autowired
	private PrestamistaRepository repo;
	@Autowired
	private UsuarioRepository usuarioRepository;
	// private PersonaRepository personaRepo;

	@Override
	public List<Prestamista> listarPrestamista() {
		return repo.findAll().stream()
				.filter(prestamista -> prestamista.isActivo() && prestamista.getPrestamista().isActivo())
				.collect(Collectors.toList());
	}

	@Override
	public Prestamista listarPrestamistaPorId(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Prestamista guardarPrestamista(Prestamista prestamista) {
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
		return repo.save(prestamista);

		// esto con la finalidad para poder actualizar da error de esto
		// detached entity passed to persist: com.cibertec.api.model.PersonaM

		// repo.save(prestamista);

	}

	// Eliminacion fisica
	/*
	 * @Override public void eliminarPrestamista(int id) { //.deleteById(id);
	 * //eliminamos por ID o COD usamos este repo.deleteById(id); }
	 */
	// ----------------------Eliminacion Logica
	@Override
	@Transactional
	public void eliminarPrestamista(int id) {
		Prestamista prestamista = repo.findById(id).orElse(null);
		if (prestamista != null) {
			prestamista.getPrestamista().setActivo(false);
			// al campo prestamista de tipo PrestamistaM lo cambia a true osea de eliminado
			prestamista.setActivo(false);
			repo.save(prestamista);

			Usuario usuario = usuarioRepository.findByPersonaIdPersona(prestamista.getIdPrestamista());
			usuario.setActivo(false);
			usuarioRepository.save(usuario);
		}
	}

	@Override
	public Optional<Prestamista> getPrestamistaById(int id) {
		return repo.findById(id);
	}

	@Override
	public Prestamista getByIdPrestamistaActivo(int idPrestamista) {
		return repo.findByIdPrestamistaAndActivo(idPrestamista, true);
	}

	@Override
	public Prestamista buscarPorDni(String dni) {
		return repo.findByPrestamistaDni(dni);
	}

	@Override
	public Prestamista buscarPorRuc(String ruc) {
		return repo.findByPrestamistaRuc(ruc);
	}

	@Override
	public Prestamista buscarPorDniOPorRuc(String dni, String ruc) {
		return repo.findByPrestamistaDniOrPrestamistaRuc(dni, ruc);
	}

	@Override
	public Prestamista buscarPorDniAndActivo(String dni) {
		return repo.findByPrestamistaDniAndActivo(dni, true);
	}
}
