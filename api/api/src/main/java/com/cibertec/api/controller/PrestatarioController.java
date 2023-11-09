package com.cibertec.api.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.Rol;
import com.cibertec.api.model.SolicitudDto;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.BancoService;
import com.cibertec.api.service.CuentaService;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestatarioService;
import com.cibertec.api.service.SolicitudPrestamoService;
import com.cibertec.api.service.UService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/prestatario")
public class PrestatarioController {

	PrestatarioService prestatarioService;
	BancoService bancoService;
	SolicitudPrestamoService solicitudPrestamoService;
	private UService userService;
	CuentaService cuentaService;
	PrestamistaService prestamistaService;

	@GetMapping("/listarPresta")
	private String listar(Model model, HttpSession session) {
		
		//ANTES
//		List<Prestatario> listaPrestatario = prestatarioService.listarPrestatario();
//		model.addAttribute("listaPrestatario",listaPrestatario);
		
//		return "listaPrestatario";
		
		//DESPUES CON FILTRO
// Obtener al Prestamista desde la session de su Usuario, ROL 3 ES PRESTAMISTA, 4 PRESTATARIO
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		// obtener el rol que deberia ser el 3 osea el prestamista
		int rolIngreso = userLogged.getRol().getIdRol();
		// Listado declarado
		List<Prestatario> lista = new ArrayList<>();
		// for mensaje 
		String titulo = "";
		String txtButton = "";
		switch (rolIngreso) {
		// admin lista de jefes

		case 1: {
			// obtener id
			int idPrestamista = userLogged.getPersona().getIdPersona();

			// Validación correspondiente
			if (idPrestamista == -1)
				return "redirect:/intranet";

			/* Listado por Prestamista */
			Rol rolPrestamista = new Rol();
			rolPrestamista.setIdRol(3);
			List<Usuario> users = userService.getUsuarioByRol(rolPrestamista);
				
			List<Prestatario> Prestamistas = new ArrayList<>();
			Prestamistas = users.stream().map(usuario -> prestatarioService.getPrestatarioById(usuario.getPersona().getIdPersona()).orElse(null))
			.collect(Collectors.toList());
			

			if (Prestamistas != null) {
				lista = Prestamistas.stream().filter(Objects::nonNull) // Filtrar
// elementos
// no
// nulos
						.collect(Collectors.toList());
			}
			//model.addAttribute("navbar", true);
			titulo = "Lista de Prestatario";
			txtButton = "Agregar Prestatario";
			break;
		}
		default:
			break;

		} // fin de switch
		model.addAttribute("lista", lista);
		model.addAttribute("titulo", titulo);
		model.addAttribute("txtButton", txtButton);
		return "listaPrestatario";
	} //fin de listar
	
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
	public String guardarPrestamista(Prestatario prestatario, BindingResult result,
			Model model, RedirectAttributes flash, SessionStatus status, HttpSession session) {
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		Prestamista prestamista = prestamistaService.listarPrestamistaPorId(userLogged.getPersona().getIdPersona());
		prestatario.setPrestamistaPrestatario(prestamista);
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
