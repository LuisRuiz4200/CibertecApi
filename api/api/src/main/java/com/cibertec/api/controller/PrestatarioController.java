package com.cibertec.api.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.support.SessionStatus;
import com.cibertec.api.model.Persona;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.service.PrestatarioService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/prestatario")
public class PrestatarioController {
	

	PrestatarioService prestatarioService;
	
	@GetMapping("/listarPresta")
	private String listar(Model model) {
		List<Prestatario> listaPrestatario = prestatarioService.listarPrestatario();
		model.addAttribute("listaPrestatario",listaPrestatario);
		
		return "listaPrestatario";
	}
	
	@GetMapping("/registrarPrestatario")
	private String registrar(Model model) {
		
		Prestatario prestatario = new Prestatario();
		//prestatario.setFechaRegistro(new Date(new java.util.Date().getTime()));
		//prestatario.setActivo(true);
		prestatario.setPrestatario(new Persona());
		
		model.addAttribute("titulo","Registrar Prestatario");
		model.addAttribute("prestatario", prestatario);
		
		return "guardarPrestatatario";
	}
	@PostMapping("/registrarPrestatario") //localhost:9090/registrar
	public String guardarPrestamista(Prestatario prestatario,BindingResult result,
			Model model,RedirectAttributes flash,SessionStatus status) {
		prestatario.setActivo(true);
		prestatario.getPrestatario().setActivo(true);
		if(result.hasErrors()) {
		
			model.addAttribute("titulo","Registrar Prestatario");
			
			return "guardarPrestatatario";
		}
		  String mensaje; 
		  // if (prestamista.getIdPrestamista() != 0) 
		  if(prestatario.getPrestatario().getIdPersona() != 0) mensaje =
		  "El Prestamista se actualizó correctamente"; else mensaje =
		  "El Prestamista se registró correctamente";
		  
		  prestatarioService.guardarPrestatario(prestatario); //Marca el status como completo.
		  status.setComplete();
		  
		  flash.addFlashAttribute("success", mensaje);
		  
		  return "redirect:/listar";
		 
	} //fin de guardarPrestamista
	
	
	
	

}
