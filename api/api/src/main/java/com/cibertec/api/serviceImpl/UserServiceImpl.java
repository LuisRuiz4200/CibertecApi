package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Usuario;
import com.cibertec.api.repository.UsuarioRepository;
import com.cibertec.api.service.UService;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UService {

	//Listado normal
	//@Override
	//public List<tbusuario> listarUsuario() {
	//return repo.findAll();
	//}
	//Listado de manera logica
	//para tema de seguridad y clave con la libreria PasswordEncoder podemos encriptar la clave
	private PasswordEncoder passwordEncoder;
	private UsuarioRepository repo;
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
		
	//Creamos objeto usuario
	Usuario usu = new Usuario();
		usu.setNombreUsuario(usuario.getNombreUsuario());
		//al campo clave de usuario con passwordEncoder encriptamos el getClave 
		usu.setClaveUsuario(passwordEncoder.encode(usuario.getClaveUsuario()));
		usu.setPersona(usuario.getPersona());
		usu.setRol(usuario.getRol());
			
		repo.save(usu);
	}
	//Eliminacion fisica
	//@Override
	//public void eliminarUsuario(int id) {
		//repo.deleteById(id);
		
	//}
	@Override
	public void eliminarUsuario(int id) {
		// TODO Auto-generated method stub
		Usuario usua = repo.findById(id).orElse(null);
	    if (usua != null) {
	        //al  campo usua de tipo tbusuario lo cambia a true osea de eliminado(1)
	    	usua.setActivo(true);
	        repo.save(usua);
	    }
	}
	

}
