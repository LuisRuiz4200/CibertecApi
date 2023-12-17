package com.cibertec.api.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.support.SessionStatus;
import com.cibertec.api.model.Banco;
import com.cibertec.api.model.Cuenta;
import com.cibertec.api.model.Persona;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.SolicitudDto;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.BancoService;
import com.cibertec.api.service.CuentaService;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestatarioService;
import com.cibertec.api.service.SolicitudPrestamoService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/prestatario")
@CrossOrigin(origins = "http://localhost:9090")
public class PrestatarioController {

	PrestatarioService prestatarioService;
	BancoService bancoService;
	SolicitudPrestamoService solicitudPrestamoService;
	CuentaService cuentaService;
	PrestamistaService prestamistaService;

	@GetMapping("/listarPresta")
	private String listar(Model model, HttpSession session) {

		// ANTES
		// List<Prestatario> listaPrestatario = prestatarioService.listarPrestatario();
		// model.addAttribute("listaPrestatario",listaPrestatario);

		// return "listaPrestatario";

		// DESPUES CON FILTRO
		// Obtener al Prestamista desde la session de su Usuario, ROL 3 ES PRESTAMISTA,
		// 4 PRESTATARIO
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		// for mensaje
		String titulo = "";
		String txtButton = "";
		// obtener id
		Prestamista prestamista = prestamistaService.listarPrestamistaPorId(userLogged.getPersona().getIdPersona());

		List<Prestatario> PrestatariosList = new ArrayList<>();
		PrestatariosList = prestatarioService.listByPrestamistaAndActivo(prestamista, true);

		// model.addAttribute("navbar", true);
		titulo = "Lista de Prestatario";
		txtButton = "Agregar Prestatario";

		model.addAttribute("lista", PrestatariosList);
		model.addAttribute("titulo", titulo);
		model.addAttribute("txtButton", txtButton);

		return "listaPrestatario";
	} // fin de listar

	@GetMapping("/registrarPrestatario")
	private String registrar(Model model, HttpSession session) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		Prestatario prestatario = new Prestatario();
		// prestatario.setFechaRegistro(new Date(new java.util.Date().getTime()));
		// prestatario.setActivo(true);
		prestatario.setPrestatario(new Persona());

		model.addAttribute("titulo", "Registrar Prestatario");
		model.addAttribute("prestatario", prestatario);

		return "guardarPrestatatario";
	}

	@PostMapping("/registrarPrestatario") // localhost:9090/registrar
	public String guardarPrestamista(Prestatario prestatario, BindingResult result,
			Model model, RedirectAttributes flash, SessionStatus status, HttpSession session) {
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		Prestamista prestamista = prestamistaService.listarPrestamistaPorId(userLogged.getPersona().getIdPersona());
		prestatario.setPrestamistaPrestatario(prestamista);
		prestatario.setActivo(true);
		prestatario.getPrestatario().setActivo(true);
		prestatario.setFechaRegistro(new java.util.Date());
		prestatario.setFechaEdicion(new java.util.Date());
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Registrar Prestatario");
			return "guardarPrestatatario";
		}
		String mensaje;
		// if (prestamista.getIdPrestamista() != 0)
		if (prestatario.getPrestatario().getIdPersona() != 0)
			mensaje = "El Prestamista se actualizó correctamente";
		else
			mensaje = "El Prestamista se registró correctamente";

		int idPersona = prestatario.getPrestatario().getIdPersona();
		if (idPersona != 0) {
			prestatarioService.guardarPrestatario(prestatario);
			return "redirect:/prestatario/listarPresta";
		}

		Prestatario newPrestatario = prestatarioService.guardarPrestatario(prestatario); // Marca el status como
																							// completo.
		status.setComplete();

		flash.addFlashAttribute("success", mensaje);

		return "redirect:/user/prestatario/" + newPrestatario.getIdPrestatario();

	} // fin de guardarPrestamista

	@GetMapping("/solicitoPrestamo")
	private String listar22(Model model, HttpSession session) {
		Usuario user = (Usuario) session.getAttribute("UserLogged");
		if (user == null)
			return "redirect:/login";

		Persona persona = user.getPersona();

		Prestatario prestatario = prestatarioService.listarPrestatarioPorId(user.getPersona().getIdPersona());
		List<Banco> bancosList = bancoService.getAll();
		SolicitudDto solicitudPrestamo = new SolicitudDto();
		solicitudPrestamo.setFechaRegistro(new Date(new java.util.Date().getTime()));
		List<Cuenta> cuentas = cuentaService.getAllByPrestatario(prestatario);
		solicitudPrestamo.setNewBank(false);

		model.addAttribute("bancos", bancosList);
		model.addAttribute("solicitudPrestamo", solicitudPrestamo);
		model.addAttribute("cuentas", cuentas);
		model.addAttribute("persona", persona);
		return "solicitudPrestamoByPrestatario";
	}

	@PostMapping("/solicitoPrestamo")
	private String guardarSolicitud(SolicitudDto solicitudPrestamo, HttpSession session, RedirectAttributes flash) {
		Usuario user = (Usuario) session.getAttribute("UserLogged");
		Prestatario prestatario = new Prestatario();
		prestatario = prestatarioService.listarPrestatarioPorId(user.getPersona().getIdPersona());

		/* Se valida que no exista más de dos solicitudes al día */
		List<SolicitudPrestamo> todayList = new ArrayList<SolicitudPrestamo>();
		todayList = solicitudPrestamoService.filtrarSolicitudes(prestatario.getIdPrestatario(),
				solicitudPrestamo.getFechaRegistro(), solicitudPrestamo.getFechaRegistro());
		if (todayList.size() >= 2) {
			flash.addFlashAttribute("errorMessage", true);
			return "redirect:/prestatario/solicitoPrestamo";
		}

		/* Se ingresa el número de cuenta si no existe */
		boolean newBank = solicitudPrestamo.isNewBank();
		if (newBank) {
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
		/*
		 * SolicitudPrestamo newSolicitud = setDataFormDto(solicitudPrestamo);
		 * newSolicitud.setPrestatario(prestatario);
		 * solicitudPrestamoService.guardar(newSolicitud);
		 * 
		 * flash.addFlashAttribute("successMessage", true);
		 * return "redirect:/prestatario/solicitoPrestamo";
		 */

		SolicitudPrestamo newSolicitud = setDataFormDto(solicitudPrestamo);
		newSolicitud.setPrestatario(prestatario);
		// Guardar la solicitud y obtener el ID
		// solicitudPrestamoService.guardar(newSolicitud);
		SolicitudPrestamo solicitudGuardada = solicitudPrestamoService.guardar(newSolicitud);
		int idSolicitud = solicitudGuardada.getIdSolicitudPrestamo();
		// Almacenamos en una variable para acceder por idSolicitud

		flash.addFlashAttribute("idSolicitud", idSolicitud);
		flash.addFlashAttribute("successMessage", true);
		// flash.addFlashAttribute("successMessage", "Solicitud generada con éxito.
		// Número de Solicitud: ");
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
		return newSolicitud;
	}
	// EDITAR

	// Metodo para actualizar
	@GetMapping("/actualizarPrestata/{id}")
	public String editarPrestamista(@PathVariable(name = "id") int id, Model model, HttpSession session,
			RedirectAttributes flash) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		// creamos objeto presta inicializado en null
		Prestatario presta = null;
		// Valida que el id sea mayor a 0
		if (id > 0) {
			// Si es válido, busca el presta en el servicio por id
			presta = prestatarioService.listarPrestatarioPorId(id);
			// Valida que existe, si no arroja error
			if (Objects.isNull(presta)) {
				flash.addFlashAttribute("error", "El ID de Prestatario no existe");
				// Retorna un redirect a la URL /listar para mostrar la lista con el atributo
				// error que almacena
				// el mensaje
				return "redirect:/prestatario/listarPresta";

			} // fin de if
		} else {
			flash.addFlashAttribute("error", "El ID de Prestatario no puede ser menor a 1");
			// Retorna un redirect a la URL /listar para mostrar la lista con el atributo
			// error que almacena
			// el mensaje
			return "redirect:/prestatario/listarPresta";
		}
		// Si existe, agrega el empleado al modelo
		// Si el empleado existe, la función lo agrega a un objeto llamado empleado
		// y lo pasaremos a la vista por medio del atributo empleado
		model.addAttribute("prestatario", presta);
		// enviamos en atributo llamado titulo, un string que
		// dice Detalle de Empleado concantenado el nombre
		model.addAttribute("titulo", "Actualización");
		// esta vista se usara para el agregar y actualizar reutilizando la vista
		return "guardarPrestatatario";
	} // fin de editarEmpleado

	// ELIMINAR
	@GetMapping("/eliminarPrestata/{id}")
	public String eliminarPrestatario(@PathVariable(name = "id") int id, HttpSession session) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		// Valida que el id sea mayor a 0
		if (id > 0) {
			prestatarioService.eliminarPrestatario(id);
		} // fin de if
		return "redirect:/prestatario/listarPresta";
	} // fin de eliminarEmpleado

	@GetMapping("/buscaCuentaExistente/{idBanco}/{cuenta}")
	@ResponseBody
	public ResponseEntity<?> cuentaExistente(@PathVariable int idBanco, @PathVariable String cuenta) {

		Optional<Cuenta> cuentaInDataBase = cuentaService.getCuentaByBancoAndNumero(idBanco, cuenta);
		HashMap<String, Boolean> response = new HashMap<>();
		response.put("exists", cuentaInDataBase.isPresent());
		return ResponseEntity.ok(response);
	}

	// ------------

	@GetMapping("/revisarEstadoPrestamo")
	private String listarPrestamoRevisar(Model model, HttpSession session) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		return "prestatarioRevisaPrestamo";
	}

	// ----------------

}
