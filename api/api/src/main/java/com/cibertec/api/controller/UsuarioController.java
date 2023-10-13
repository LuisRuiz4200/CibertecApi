package com.cibertec.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class UsuarioController {

	@RequestMapping("/login")
	public String login(){
		//Si ponemos en la ruta localhost:9090/login nos redirecciona AQUI 
		return "inicio";
	}
}
