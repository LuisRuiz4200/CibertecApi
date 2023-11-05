package com.cibertec.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.cibertec.api.model.Banco;
import com.cibertec.api.model.Cuenta;
import com.cibertec.api.model.Persona;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.BancoService;
import com.cibertec.api.service.CuentaService;
import com.cibertec.api.service.PrestatarioService;
import com.cibertec.api.service.SolicitudPrestamoService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/prestatario")
public class PrestatarioController {

	PrestatarioService prestatarioService;
	BancoService bancoService;
	SolicitudPrestamoService solicitudPrestamoService;
	CuentaService cuentaService;

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
	
	@GetMapping("/solicitoPrestamo")
	private String listar22(Model model) {
		List<Banco> bancosList = bancoService.getAll();
		SolicitudPrestamo solicitudPrestamo = new SolicitudPrestamo();
		solicitudPrestamo.setFechaRegistro(new Date(new java.util.Date().getTime()));
		Prestatario prestatario = prestatarioService.listarPrestatarioPorId(32);
		List<Cuenta> cuentas =  cuentaService.getAllByPrestatario(prestatario);
		print(cuentas);

		model.addAttribute("bancos", bancosList);
		model.addAttribute("solicitudPrestamo", solicitudPrestamo);
		model.addAttribute("cuentas", cuentas);
		return "solicitudPrestamoByPrestatario";
	}

	@PostMapping("/solicitoPrestamo")
	private String guardarSolicitud(SolicitudPrestamo solicitudPrestamo, HttpSession session){
		Usuario user = (Usuario)session.getAttribute("UserLogged");
		// TODO: Usuario de prestatario
		Prestatario prestatario = prestatarioService.listarPrestatarioPorId(32);
		solicitudPrestamo.setPrestatario(prestatario);
		solicitudPrestamo.setActivo(true);
		solicitudPrestamo.setEstado("Pendiente");

		print(solicitudPrestamo);
		solicitudPrestamoService.guardar(solicitudPrestamo);

		// return "redirect:/intranet";
		return "solicitudPrestamoByPrestatario";
	}

	private void print(Object object){
		System.out.println("\n\n======================");
		System.out.println(object);
		System.out.println("======================\n\n");
	}

}
