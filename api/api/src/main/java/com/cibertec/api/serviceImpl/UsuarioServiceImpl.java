package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cibertec.api.model.Usuario;
import com.cibertec.api.repository.UsuarioRepository;
import com.cibertec.api.service.UService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioServiceImpl implements UService {
	
	private UsuarioRepository repo;
	//Listado normal
	//@Override
	//public List<tbusuario> listarUsuario() {
		//return repo.findAll();
	//}
	//Listado de manera logica
	@Override
	  public List<Usuario> listarUsuario() {
	      return repo.findAll().stream()
	   .filter(usuario -> !usuario.isActivo())
	   .collect(Collectors.toList());
	  }
	
	

	@Override
	public Usuario listarUsuarioPorId(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void guardarUsuario(Usuario usuario) {
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
	    Usuario usua = repo.findById(id).orElse(null);
	    if (usua != null) {
	        //al  campo usua de tipo tbusuario lo cambia a true osea de eliminado(1)
	    	usua.setActivo(true);
	        repo.save(usua);
	    }
	}

	
	
	
	
	
} //fin de usuarioServiceImpl
