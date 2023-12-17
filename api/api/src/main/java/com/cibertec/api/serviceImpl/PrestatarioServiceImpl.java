package com.cibertec.api.serviceImpl;

import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Prestatario;

import com.cibertec.api.repository.PrestatarioRepository;
import com.cibertec.api.service.PrestatarioService;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PrestatarioServiceImpl implements PrestatarioService {

	private PrestatarioRepository repo;

	@Override
	public List<Prestatario> listarPrestatario() {
		// return repo.findAll();
		return repo.findAll().stream()
				.filter(prestatario -> prestatario.isActivo() && prestatario.getPrestatario().isActivo())
				.collect(Collectors.toList());
	}

	@Override
	public Prestatario listarPrestatarioPorId(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Prestatario guardarPrestatario(Prestatario prestatario) {
		return repo.save(prestatario);
	}

	@Override
	public void eliminarPrestatario(int id) {
		Prestatario prestatario = repo.findById(id).orElse(null);
		if (prestatario != null) {
			prestatario.getPrestatario().setActivo(false);
			// al campo prestamista de tipo PrestamistaM lo cambia a true osea de eliminado
			prestatario.setActivo(false);
			repo.save(prestatario);
		}

	}

	@Override
	public Optional<Prestatario> getPrestatarioById(int id) {
		return repo.findById(id);
	}

	@Override
	public List<Prestatario> listByPrestamistaAndActivo(Prestamista Prestamista, boolean activo) {
		return repo.findByPrestamistaPrestatarioAndActivo(Prestamista, activo);

	}

	@Override
	public Prestatario buscarPorDni(String dni) {
		return repo.findByPrestatarioDni(dni);
	}

	@Override
	public Prestatario buscarPorRuc(String ruc) {
		return repo.findByPrestatarioRuc(ruc);
	}

	@Override
	public Prestatario buscarPorDniOPorRuc(String dni, String ruc) {
		return repo.findByPrestatarioDniOrPrestatarioRuc(dni, ruc);
	}

}
