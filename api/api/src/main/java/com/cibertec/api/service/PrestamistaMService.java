package com.cibertec.api.service;
import java.util.List;
import java.util.Optional;

import com.cibertec.api.model.Prestamista;

public interface PrestamistaMService {
	
			//Creamos metodos para CRUD	
	
			public List<Prestamista> listarPrestamista();
			
			public Prestamista listarPrestamistaPorId(int id);
			
			public void guardarPrestamista(Prestamista prestamista);
	
			public void eliminarPrestamista(int id);	
	
			public abstract Optional<Prestamista> getPrestamistaById(int id);
	

} //fin de PrestamitaMService
