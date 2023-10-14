package com.cibertec.api.service;

import java.util.List;

import com.cibertec.api.model.Menu;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.repository.UsuarioRepository;
import java.util.stream.Collectors;
//import com.clinica.project.entity.Enlace;
//import com.clinica.project.entity.Usuario;


public interface usuarioService {
	
	//Creamos metodos para CRUD	
	
	public Usuario loginUsuario(String vLogin) {
		return repo.iniciarSesion(vLogin);
	}
	public List<Menu> enlacesDelUsuario(int rol){
		return repo.traerMenusDelUsuario(rol);
	}
	//Creamos metodos para CRUD	
	
}
