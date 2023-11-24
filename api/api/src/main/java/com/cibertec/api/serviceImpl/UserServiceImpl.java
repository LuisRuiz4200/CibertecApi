package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Rol;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.repository.UsuarioRepository;
import com.cibertec.api.service.UService;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UService {

	
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UsuarioRepository repo;
	
	
	@Override
	public List<Usuario> listarUsuario() {
		  return repo.findAll().stream()
				   .filter(usuario -> usuario.isActivo())
				   .collect(Collectors.toList());
	}

	@Override
	public Usuario listarUsuarioPorId(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void guardarUsuario(Usuario usuario) {
		
	//Creamos objeto usuario
	/*Usuario usu = new Usuario();
		usu.setNombreUsuario(usuario.getNombreUsuario());
		//al campo clave de usuario con passwordEncoder encriptamos el getClave 
		usu.setClaveUsuario(passwordEncoder.encode(usuario.getClaveUsuario()));
		usu.setPersona(usuario.getPersona());
		usu.setRol(usuario.getRol());
			
		repo.save(usu);*/
		// Buscamos el usuario existente en la base de datos
	    Optional<Usuario> optionalUsu = repo.findById(usuario.getIdUsuario());
	    
	    if (optionalUsu.isPresent()) {
	        // Si encontramos el usuario, actualizamos los campos necesarios
	        Usuario usu = optionalUsu.get();
	        usu.setNombreUsuario(usuario.getNombreUsuario());
	        usu.setClaveUsuario(passwordEncoder.encode(usuario.getClaveUsuario()));
	        usu.setPersona(usuario.getPersona());
	        usu.setRol(usuario.getRol());
	        usu.setActivo(true);
	        
	        // Guardamos los cambios
	        repo.save(usu);
	    } else {
	        // Si no encontramos el usuario, creamos uno nuevo
	        Usuario usu = new Usuario();
	        usu.setNombreUsuario(usuario.getNombreUsuario());
	        usu.setClaveUsuario(passwordEncoder.encode(usuario.getClaveUsuario()));
	        usu.setPersona(usuario.getPersona());
	        usu.setRol(usuario.getRol());
	        usu.setActivo(true);
	        
	        // Guardamos el nuevo usuario
	        repo.save(usu);
	    }
		
	}
	//Eliminacion fisica
	//@Override
	//public void eliminarUsuario(int id) {
		//repo.deleteById(id);
		
	//}
	@Override
	public void eliminarUsuario(int id) {
		Usuario usua = repo.findById(id).orElse(null);
	    if (usua != null) {
	        //al  campo usua de tipo tbusuario lo cambia a false osea de eliminado(1)
	    	usua.setActivo(false);
	        repo.save(usua);
	    }
	}

	@Override
	public List<Usuario> getUsuarioByRol(Rol rol) {
		return repo.findByRol(rol);
	}
	
	@Override
	public Usuario buscarPorNombreUsuario(String nombreUsuario) {
		return repo.findByNombreUsuario(nombreUsuario);
	}
	

}
