package com.cibertec.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.clinica.project.entity.Enlace;
//import com.clinica.project.entity.Usuario;

import com.cibertec.api.model.Menu;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository repo;
	// Creamos metodos para CRUD

	public Usuario loginUsuario(String vLogin) {
		return repo.iniciarSesion(vLogin);
	}

	public List<Menu> enlacesDelUsuario(int rol) {
		return repo.traerMenusDelUsuario(rol);
	}
	// Creamos metodos para CRUD

}
