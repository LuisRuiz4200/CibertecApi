package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.tbusuario;

import com.cibertec.api.repository.PrestamistaRepository;
import com.cibertec.api.repository.UsuarioRepository;
import com.cibertec.api.service.usuarioService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class usuarioServiceImpl implements usuarioService {
	
	private UsuarioRepository repo;
	//Listado normal
	//@Override
	//public List<tbusuario> listarUsuario() {
		//return repo.findAll();
	//}
	//Listado de manera logica
	@Override
	  public List<tbusuario> listarUsuario() {
	      return repo.findAll().stream()
	   .filter(usuario -> !usuario.isActivo())
	   .collect(Collectors.toList());
	  }
	
	

	@Override
	public tbusuario listarUsuarioPorId(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void guardarUsuario(tbusuario usuario) {
		repo.save(usuario);
		
	}
	//Eliminacion fisica
	//@Override
	//public void eliminarUsuario(int id) {
		//repo.deleteById(id);
		
	//}
	
	//eliminacion logica
	@Override
	public void eliminarUsuario(int id) {
	    tbusuario usua = repo.findById(id).orElse(null);
	    if (usua != null) {
	        //al  campo usua de tipo tbusuario lo cambia a true osea de eliminado(1)
	    	usua.setActivo(true);
	        repo.save(usua);
	    }
	}
	
	
	
	
	
} //fin de usuarioServiceImpl
