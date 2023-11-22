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

import com.cibertec.api.model.Comprobante;
import com.cibertec.api.model.TipoComprobante;
import com.cibertec.api.model.TipoDocumento;
import com.cibertec.api.service.ComprobanteService;
import com.cibertec.api.service.TipoComprobanteService;
import com.cibertec.api.service.TipoDocumentoService;

@Controller
@RequestMapping("web/comprobante")
public class ComprobanteController {
	
	@Autowired
	private ComprobanteService comprobanteService;
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	@Autowired
	private TipoComprobanteService tipoComprobanteService;

	@GetMapping("/registrar")
	private String registrar(Model model) {
		
		Comprobante comprobante = new Comprobante();
		List<TipoComprobante> listaTipoComprobante = new ArrayList<>();
		List<TipoDocumento> listaTipoDocumento = new ArrayList<>();
		
		String mensaje = "";
		
		
		try {
			
			listaTipoComprobante = tipoComprobanteService.listar().stream().filter(tipo->!tipo.getDescripcion().equals("FACTURA")).toList();
			listaTipoDocumento = tipoDocumentoService.listar();
			
		}catch(Exception ex) {
			mensaje = ex.getMessage();
		}
		
		model.addAttribute("mensaje",mensaje);
		model.addAttribute("comprobante",comprobante);
		model.addAttribute("listaTipoComprobante",listaTipoComprobante);
		model.addAttribute("listaTipoDocumento",listaTipoDocumento);
		
		
		return "guardarComprobante";
	}
	@GetMapping("/actualizar/{id}")
	private String actualizar(@PathVariable int id, Model model) {
		
		Comprobante comprobante = new Comprobante();
		String mensaje = "";
		
		try {
			comprobante = comprobanteService.buscarPorId(id);
		}catch(Exception ex) {
			mensaje = ex.getMessage();
		}
		
		model.addAttribute("mensaje",mensaje);
		model.addAttribute("comprobante",comprobante);
		
		
		return "guardarComprobante";
	}
	
	@PostMapping("/guardar")
	private String guardar(@ModelAttribute Comprobante comprobante,Model model) {
		
		String mensaje = "";
		
		try {
			
			comprobante = comprobanteService.guardar(comprobante);
				
			
		}catch(Exception ex) {
			mensaje = ex.getMessage();
		}
		
		model.addAttribute("mensaje",mensaje);
		model.addAttribute("comprobante",comprobante);
		
		return "guardarComprobante";
	}
}
