package com.cibertec.api.service;
import java.util.List;

import com.cibertec.api.model.Prestamista;

public interface PrestamistaService {
	
	//Creamos metodos para CRUD	
	
	public List<Prestamista> listarPrestamista();
			
	public Prestamista listarPrestamistaPorId(int id);
			
	public Prestamista guardarPrestamista(Prestamista prestamista);
	
	public void eliminarPrestamista(int id);	
	
	

} //fin de PrestamitaMService
