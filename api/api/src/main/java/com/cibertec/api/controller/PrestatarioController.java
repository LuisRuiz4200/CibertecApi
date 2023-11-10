package com.cibertec.api.controller;

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
import com.cibertec.api.model.SolicitudDto;
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
		prestatario.setFechaRegistro(new java.util.Date());
		prestatario.setFechaEdicion(new java.util.Date());
		if(result.hasErrors()) {
			model.addAttribute("titulo","Registrar Prestatario");
			return "guardarPrestatatario";
		}
		  String mensaje;
		  // if (prestamista.getIdPrestamista() != 0)
		  if(prestatario.getPrestatario().getIdPersona() != 0) mensaje =
		  "El Prestamista se actualizó correctamente"; else mensaje =
		  "El Prestamista se registró correctamente";
		  
		  Prestatario newPrestatario = prestatarioService.guardarPrestatario(prestatario); //Marca el status como completo.
		  status.setComplete();
		  
		  flash.addFlashAttribute("success", mensaje);
		  
		  return "redirect:/user/prestatario/" + newPrestatario.getIdPrestatario();
		 
	} //fin de guardarPrestamista
	
	@GetMapping("/solicitoPrestamo")
	private String listar22(Model model, HttpSession session) {
		Usuario user = (Usuario)session.getAttribute("UserLogged");
		Persona persona = user.getPersona();

		Prestatario prestatario = prestatarioService.listarPrestatarioPorId(user.getPersona().getIdPersona());
		List<Banco> bancosList = bancoService.getAll();
		SolicitudDto solicitudPrestamo = new SolicitudDto();
		solicitudPrestamo.setFechaRegistro(new Date(new java.util.Date().getTime()));
		List<Cuenta> cuentas =  cuentaService.getAllByPrestatario(prestatario);
		solicitudPrestamo.setNewBank(false);

		model.addAttribute("bancos", bancosList);
		model.addAttribute("solicitudPrestamo", solicitudPrestamo);
		model.addAttribute("cuentas", cuentas);
		model.addAttribute("persona", persona);
		return "solicitudPrestamoByPrestatario";
	}

	@PostMapping("/solicitoPrestamo")
	private String guardarSolicitud(SolicitudDto solicitudPrestamo, HttpSession session, RedirectAttributes flash){
		Usuario user = (Usuario)session.getAttribute("UserLogged");
		Prestatario prestatario = prestatarioService.listarPrestatarioPorId(user.getPersona().getIdPersona());

		/* Se valida que no exista más de dos solicitudes al día */
		List<SolicitudPrestamo> todayList = solicitudPrestamoService.filtrarSolicitudes(prestatario.getIdPrestatario(), solicitudPrestamo.getFechaRegistro(), solicitudPrestamo.getFechaRegistro());
		if(todayList.size() >= 2){
			flash.addFlashAttribute("errorMessage", true);
			return "redirect:/prestatario/solicitoPrestamo";
		}

		/* Se ingresa el número de cuenta si no existe */
		boolean newBank = solicitudPrestamo.isNewBank();
		if(newBank){
			Cuenta newCuenta = solicitudPrestamo.getCuentaSolicitud();
			newCuenta.setNumero(solicitudPrestamo.getCuentaBancaria());
			newCuenta.setIdPrestatarioCuenta(prestatario);
			newCuenta.setActivo(true);
			newCuenta.setFechaRegistro(new java.util.Date());
			newCuenta.setFechaEdicion(new java.util.Date());
			newCuenta.setUsuarioRegistro(prestatario.getIdPrestatario());
			newCuenta.setUsuarioEdicion(prestatario.getIdPrestatario());
			Cuenta cuenta = cuentaService.addOrUpdate(newCuenta);
			solicitudPrestamo.setCuentaSolicitud(cuenta);
		}

		/* El prestatario Ingresa la solicitud */
		SolicitudPrestamo newSolicitud = setDataFormDto(solicitudPrestamo);
		newSolicitud.setPrestatario(prestatario);
		solicitudPrestamoService.guardar(newSolicitud);

		flash.addFlashAttribute("successMessage", true);
		return "redirect:/prestatario/solicitoPrestamo";
	}

	private SolicitudPrestamo setDataFormDto(SolicitudDto solicitudDto) {
		SolicitudPrestamo newSolicitud = new SolicitudPrestamo();
		newSolicitud.setMonto(solicitudDto.getMonto());
		newSolicitud.setCuotas(solicitudDto.getCuotas());
		newSolicitud.setInteres(solicitudDto.getInteres());
		newSolicitud.setFechaRegistro(solicitudDto.getFechaRegistro());
		newSolicitud.setCuentaBancaria(solicitudDto.getCuentaBancaria());
		newSolicitud.setCuentaSolicitud(solicitudDto.getCuentaSolicitud());
		newSolicitud.setActivo(true);
		newSolicitud.setEstado("Pendiente");
		return  newSolicitud;
	}

}
