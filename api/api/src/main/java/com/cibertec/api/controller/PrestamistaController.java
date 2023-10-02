package com.cibertec.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.service.PrestamistaService;

@RestController
@RequestMapping("/prestamista")
public class PrestamistaController {

	@Autowired
	PrestamistaService prestamistaService ;
	
	@GetMapping("/listar")
	@ResponseBody
	private ResponseEntity<List<Prestamista>> listar() {
		List<Prestamista> response= prestamistaService.listar();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/registrar")
	@ResponseBody
	private String registrar(@RequestBody Prestamista model) {
		
		String response = "";
		
		try {

			model = prestamistaService.guardar(model);
			response = "Se ha registrado el ID " + model.getIdPrestamista() + ".";
		}catch(Exception ex){
			response = ex.getMessage();
		}
		
		return response;
	}
	
	
}
