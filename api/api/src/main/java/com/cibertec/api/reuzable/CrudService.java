package com.cibertec.api.reuzable;

import java.util.List;

public interface CrudService<T> {

	public T guardar(T model);
	public List<T> listar();
	public void eliminar(int id);
	public List<T> listarPorId(int id);
	public T buscarPorId(int id);
	
}
