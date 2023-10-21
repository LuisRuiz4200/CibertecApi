package com.cibertec.api.service;
import java.util.List;
import java.util.Optional;

import com.cibertec.api.model.Prestamista;

public interface PrestamistaService {
	
	//Creamos metodos para CRUD	
	
	public List<Prestamista> listarPrestamista();
			
	public Prestamista listarPrestamistaPorId(int id);
			
	public Prestamista guardarPrestamista(Prestamista prestamista);
	
	public void eliminarPrestamista(int id);

	Optional<Prestamista> getPrestamistaById(int id);	
	
	Prestamista getByIdPrestamistaActivo(int idPrestamista);
	
	Prestamista buscarPorDni(String dni);

	Prestamista buscarPorDniOPorRuc(String dni, String ruc);

	Prestamista buscarPorRuc(String ruc);

} //fin de PrestamitaMService
