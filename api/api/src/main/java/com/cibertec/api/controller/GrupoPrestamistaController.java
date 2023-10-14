package com.cibertec.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.service.GrupoPrestamistaService;

@RestController
@RequestMapping("/grupoPrestamista")
public class GrupoPrestamistaController {
	
	@Autowired 
	GrupoPrestamistaService grupoprestamistaService;
	
	@GetMapping("/listar")
	private ResponseEntity<List<GrupoPrestamista>> listar(){
		List<GrupoPrestamista> response = grupoprestamistaService.getGrupoPrestamistaList();
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/registrar")
	private String resgistrar(@RequestBody GrupoPrestamista model) {
		String response = "";
		try {
			model = grupoprestamistaService.saveGrupoPrestamista(model);
		} catch (Exception ex) {
			response = ex.getMessage();
		}
		
		return response;
	}
	
	/*@PutMapping("/actualizar")
	private actualizar( GrupoPrestamista model) {
		
	}*/

}
