package com.cibertec.api.service;

import java.util.List;

import com.cibertec.api.model.Rol;
import com.cibertec.api.model.Usuario;

public interface UService {
	
	//Creamos metodos para CRUD	
	
	public List<Usuario> listarUsuario();
		
	public Usuario listarUsuarioPorId(int id);
		
	public void guardarUsuario(Usuario usuario);

	public void eliminarUsuario(int id);

	List<Usuario> getUsuarioByRol(Rol rol);

	Usuario buscarPorNombreUsuario(String nombreUsuario);
}
