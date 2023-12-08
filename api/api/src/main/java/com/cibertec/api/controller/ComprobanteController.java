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
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.api.model.Comprobante;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.TipoComprobante;
import com.cibertec.api.model.TipoDocumento;
import com.cibertec.api.service.ComprobanteService;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestatarioService;
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
	@Autowired
	private PrestamistaService prestamistaService;
	@Autowired
	private PrestatarioService prestatarioService;
	
	
	@GetMapping("/listaCuotaPorPrestamo")
	private String listarCuotaPorPrestamo(Model model) {
		
		List<Prestatario> listaPrestatario = new ArrayList();
		
		listaPrestatario = prestatarioService.listarPrestatario().stream()
				.filter(c->c.getPrestamistaPrestatario().getIdPrestamista()==59).toList();
		
		model.addAttribute("listaPrestatario",listaPrestatario);
		
		return "listaCuotaPorPrestamo";
	}

	@GetMapping("/registrar")
	private String registrar(Model model,
			@RequestParam(name="idPrestamo",required = false)Integer idPrestamo,
			@RequestParam(name="idCuotaPrestamo",required = false)Integer idCuortaPrestamo) {
		
		Comprobante comprobante = new Comprobante();
		List<TipoComprobante> listaTipoComprobante = new ArrayList<>();
		List<TipoDocumento> listaTipoDocumento = new ArrayList<>();
		
		String mensaje = "";
		
		try {
			
			comprobante.setRucEmisor("20759630049");
			comprobante.setNomEmisor("ME PRESTA ONLINE SAC SAC");
			
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
