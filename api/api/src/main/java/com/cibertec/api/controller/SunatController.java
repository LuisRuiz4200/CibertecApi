package com.cibertec.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.reuzable.ApiPeru;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api")
public class SunatController {
	
	@GetMapping("/consulta/dni/{numDocumento}")
	public String consultaDni(@PathVariable(value="numDocumento") String numDocumento) {
		
		String res = "";
		
		res = ApiPeru.consultaPersona("dni",numDocumento);
		
		return res;
	}
	
	@GetMapping("/consulta/ruc")
	public String consultaRuc() {
		return "";
	}
	
}
