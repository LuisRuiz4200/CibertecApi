package com.cibertec.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.UsuarioService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	
	@GetMapping("/listar")
	private List<Usuario> listar (){
		
		return usuarioService.listar();
		
	}
	
	@PostMapping("/registrar")
	private String registrar(@RequestBody Usuario model) {
		String response = "";
		
		try {
			model = usuarioService.guardar(model);
			
			response = "Se ha registrado el ID " + model.getIdUsuario();
			
		}catch(Exception ex) {
			response = ex.getMessage();
		}
		
		return response;
		
	}
	@PutMapping("/actualizar")
	private Map<?, ?> actualizar(@RequestBody Usuario model) {
		
		Map<String, Object> response = new HashMap<String,Object>();
		
		try {
			model = usuarioService.guardar(model);
			response.put("mensaje", "Se ha actualizado el ID " + model.getIdUsuario() + ".");
		}catch(Exception ex){
			response.put("mensaje",ex.getMessage());
		}
		
		return response;
	}
	
}
