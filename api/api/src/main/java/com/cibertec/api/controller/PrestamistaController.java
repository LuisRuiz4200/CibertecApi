package com.cibertec.api.controller;

import java.util.List;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.service.PrestamistaService;

@Controller
@RequestMapping("/prestamistaWeb")
public class PrestamistaController {

	@Autowired
	PrestamistaService prestamistaService;
	
	@GetMapping("/listar")
	private String listar(Model model) {
		
		List<Prestamista> listaPrestamista = prestamistaService.listar();
		
		model.addAttribute("listaPrestamista", listaPrestamista);
		
		return "listaPrestamista";
	}
	
}
