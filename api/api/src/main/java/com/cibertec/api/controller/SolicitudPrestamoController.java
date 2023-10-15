package com.cibertec.api.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestatarioService;
import com.cibertec.api.service.SolicitudPrestamoService;

@Controller
@RequestMapping("/solicitudPrestamo")
public class SolicitudPrestamoController {
	
	@Autowired
	SolicitudPrestamoService solicitudPrestamoService;
	@Autowired
	PrestamistaService prestamistaService;
	@Autowired
	PrestatarioService prestatarioService;
	
	@GetMapping("/listar")
	private String listar(Model model) {
		
		List<SolicitudPrestamo> listaSolicitudPrestamo = new ArrayList<SolicitudPrestamo>();
		listaSolicitudPrestamo = solicitudPrestamoService.listar();
		List<Prestatario> listaPrestatario = prestatarioService.listar();
		Prestatario prestatario = new Prestatario();
		
		
		model.addAttribute("listaSolicitudPrestamo",listaSolicitudPrestamo);
		model.addAttribute("listaPrestatario",listaPrestatario);
		model.addAttribute("prestatario",prestatario);
		
		return "listaSolicitudPrestamo";
	}
	
	@GetMapping("/listar/")
	private String listarPorPrestamista(@RequestParam("idPrestatario")int idPrestatario , Model model) {
		
		List<SolicitudPrestamo> listaSolicitudPrestamo = new ArrayList<SolicitudPrestamo>();
		listaSolicitudPrestamo = solicitudPrestamoService.listarPorPrestatario(idPrestatario);
		List<Prestatario> listaPrestatario = prestatarioService.listar();
		Prestatario prestatario = prestatarioService.buscarPorId(idPrestatario);
		
		
		model.addAttribute("prestatario",prestatario);
		model.addAttribute("listaSolicitudPrestamo",listaSolicitudPrestamo);
		model.addAttribute("listaPrestatario",listaPrestatario);
		
		return "listaSolicitudPrestamo";
	}
	
	@GetMapping("/registrar")
	private String registrar(Model model) {
		
		SolicitudPrestamo solicitudPrestamo = new SolicitudPrestamo();
		solicitudPrestamo.setEstado("PENDIENTE DE ENVIO");
		solicitudPrestamo.setFechaRegistro(new Date(new java.util.Date().getTime()));
		
		List<Prestatario> listaPrestatario = prestatarioService.listar();
		
		
		model.addAttribute("solicitudPrestamo",solicitudPrestamo);
		model.addAttribute("listaPrestatario",listaPrestatario);
		
		return "guardarSolicitudPrestamo";
	}
	@PostMapping("/guardar")
	private String registrar(@ModelAttribute SolicitudPrestamo solicitudPrestamo) {
		
		solicitudPrestamoService.guardar(solicitudPrestamo);
		
		return "redirect:/solicitudPrestamo/registrar";
	}

}
