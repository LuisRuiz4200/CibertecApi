package com.cibertec.api.service;

import java.util.List;

import com.cibertec.api.model.tbusuario;


public interface usuarioService {
	
	//Creamos metodos para CRUD	
	
	public List<tbusuario> listarUsuario();
	
	public tbusuario listarUsuarioPorId(int id);
	
	public void guardarUsuario(tbusuario usuario);

	public void eliminarUsuario(int id);

}
