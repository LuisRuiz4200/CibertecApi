package com.cibertec.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.Menu;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.model.tbusuario;

@Repository
public interface UsuarioRepository extends JpaRepository<tbusuario,Integer>{

	
	// select * from tb_usuario where nombreUsuario='Julian13' --> es admin
	@Query("select u from Usuario u where u.nombreUsuario=?1")
	public Usuario iniciarSesion(String vLogin);
	
	@Query("select m from RolMenu rm join rm.menu m where rm.rol.idRol=?1")
	public List<Menu> traerMenusDelUsuario(int idRol);
	
}
