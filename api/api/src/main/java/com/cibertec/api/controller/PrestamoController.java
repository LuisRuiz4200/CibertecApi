package com.cibertec.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.api.model.Prestamo;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestamoService;
import com.cibertec.api.service.SolicitudPrestamoService;


@Controller
@RequestMapping("/web/prestamo/")
public class PrestamoController {
	
	@Autowired
	PrestamoService prestamoService;
	@Autowired
	SolicitudPrestamoService solicitudPrestamoService;
	@Autowired
	PrestamistaService prestamistaService;
	
	
	@GetMapping("/registrar/{id}")
	private String registrar(@PathVariable int id,  Model model) {
		
		Prestamo prestamo = new Prestamo();
		SolicitudPrestamo solicitudPrestamo = new SolicitudPrestamo();
		//Prestamista prestamista = new Prestamista();
		
		solicitudPrestamo = solicitudPrestamoService.buscarPorId(id);
		//prestamista = prestamistaService.listarPrestamistaPorId(id);
		
		prestamo.setSolicitudPrestamo(solicitudPrestamo);
		
		model.addAttribute("prestamo", prestamo);
		
		return "guardarPrestamo";
	}
	@GetMapping("/listar")
	private String listar (Model model) {
		
		List<Prestamo> listaPrestamo = new ArrayList<Prestamo>();
		listaPrestamo = prestamoService.listar();
		
		model.addAttribute("listaPrestamo",listaPrestamo);
		
		return "listaPrestamo";
	}
	
	@PostMapping("/registrar")
	private String registrar(@ModelAttribute Prestamo prestamo) {
		
		
		
		return "";
	}
	
}
