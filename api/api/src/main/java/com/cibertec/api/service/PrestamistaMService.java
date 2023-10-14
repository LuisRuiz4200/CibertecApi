package com.cibertec.api.service;
import java.util.List;
import java.util.Optional;

import com.cibertec.api.model.PrestamistaM;

public interface PrestamistaMService {
	
			//Creamos metodos para CRUD	
	
			public List<PrestamistaM> listarPrestamista();
			
			public PrestamistaM listarPrestamistaPorId(int id);
			
			public void guardarPrestamista(PrestamistaM prestamista);
	
			public void eliminarPrestamista(int id);	
	
			public abstract Optional<PrestamistaM> getPrestamistaById(int id);
	

} //fin de PrestamitaMService
