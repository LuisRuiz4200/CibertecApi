package com.cibertec.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.Prestatario;
import com.cibertec.api.service.PrestatarioService;

@RestController
@RequestMapping("/prestatario")
public class PrestatarioController {

	@Autowired
	PrestatarioService prestatarioService;
	
	@GetMapping("/listar")
	private List<Prestatario> listar(){
		List<Prestatario> response = prestatarioService.listar();
		return response;
	}
	
	@PostMapping("/registrar")
	private String registrar(@RequestBody Prestatario model) {
		String response = "";
		try {
			model = prestatarioService.guardar(model);
			response ="Se ha registrado el ID " + model.getPrestatario().getIdPersona() + "";
		}catch(Exception ex) {
			response = ex.getMessage();
		}
		return response;
	}
	
}
