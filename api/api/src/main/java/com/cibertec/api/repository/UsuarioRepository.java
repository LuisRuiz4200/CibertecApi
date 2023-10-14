package com.cibertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.Usuario;
import com.cibertec.api.model.tbusuario;

@Repository
public interface UsuarioRepository extends JpaRepository<tbusuario,Integer>{

	Usuario iniciarSesion(String username);

}
