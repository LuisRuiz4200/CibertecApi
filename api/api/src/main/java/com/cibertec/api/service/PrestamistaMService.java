package com.cibertec.api.service;
import java.util.List;

import com.cibertec.api.model.PrestamistaM;

public interface PrestamistaMService {
	
	//Creamos metodos para CRUD	
	
	public List<PrestamistaM> listarPrestamista();
			
	public PrestamistaM listarPrestamistaPorId(int id);
			
	public void guardarPrestamista(PrestamistaM prestamista);
	
	public void eliminarPrestamista(int id);	
	
	

} //fin de PrestamitaMService
