package com.cibertec.api.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.api.model.Prestatario;
import com.cibertec.api.service.PrestatarioService;

@Controller
@RequestMapping("/prestatario")
public class PrestatarioController {
	
	@Autowired
	PrestatarioService prestatarioService;
	
	@GetMapping("/listar")
	private String listar(Model model) {
		List<Prestatario> listaPrestatario = prestatarioService.listar();
		model.addAttribute("listaPrestatario",listaPrestatario);
		
		return "listaPrestatario";
	}
	
	@GetMapping("/registrar")
	private String registrar(Model model) {
		
		Prestatario prestatario = new Prestatario();
		prestatario.setFechaRegistro(new Date(new java.util.Date().getTime()));
		prestatario.setActivo(true);
		
		model.addAttribute("prestatario", prestatario);
		
		return "guardarPrestatario";
	}
	
	

}
