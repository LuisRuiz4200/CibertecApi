package com.cibertec.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.api.model.Persona;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Rol;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.UService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PrestamistaController {

	// ----
	// service
	private PrestamistaService service;
	private GrupoPrestamistaController grupoController;
	private UService userService;

	@GetMapping({ "/listar", "/", "" }) // localhost:9090 /
	public String listarPrestamista(Model model, HttpSession session) {
		// Obtener al JefePrestamista desde la session de su Usuario
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");

		// obtener el rol
		int rolIngreso = userLogged.getRol().getIdRol();
		// System.out.println(rolIngreso);
		// Listado declarado
		List<Prestamista> lista = new ArrayList<>();
		// for mensaje
		String titulo = "";
		String txtButton = "";
		switch (rolIngreso) {
		// admin lista de jefes

		case 1: {
			// obtener id
			int idAdministrador = userLogged.getPersona().getIdPersona();

			// Validación correspondiente
			if (idAdministrador == -1)
				return "redirect:/intranet";

			/* Listado por Jefes */
			Rol rolJefes = new Rol();
			rolJefes.setIdRol(2);
			List<Usuario> users = userService.getUsuarioByRol(rolJefes);

			List<Prestamista> jefesPrestamistas = new ArrayList<>();
			jefesPrestamistas = users.stream()
					.map(usuario -> service.getPrestamistaById(usuario.getPersona().getIdPersona()).orElse(null))
					.collect(Collectors.toList());
			

			if (jefesPrestamistas != null) {
				lista = jefesPrestamistas.stream().filter(Objects::nonNull) // Filtrar
																															// elementos
																															// no
																															// nulos
						.collect(Collectors.toList());
			}
			model.addAttribute("navbar", true);
			titulo = "Lista de Jefes de Prestamista";
			txtButton = "Agregar Jefe Prestamista";
			break;
		}
		// jefe de prestamista, lista de prestamistas
		case 2: {
			int idJefePrestamista = userLogged.getPersona().getIdPersona();
			Prestamista jefePrestamista = service.getPrestamistaById(idJefePrestamista).orElse(null);
			// Validación correspondiente
			if (jefePrestamista == null)
				return "redirect:/intranet";
			// Obtener la lista de prestamistas asociado al Jefe y que estén Activos
			lista = grupoController.listGrupoByJefePrestamistaAndActivo(jefePrestamista);
			titulo = "Lista de Prestamistas";
			model.addAttribute("navbar", false);
			txtButton = "Agregar Prestamista";
			break;
		}
		default:
			break;

		} // fin de switch
		model.addAttribute("lista", lista);
		model.addAttribute("titulo", titulo);
		model.addAttribute("txtButton", txtButton);
		return "listar";
	} // fin de listarPrestamista

	@GetMapping("/registrar")
	public String mostrarFormularioRegistroPrestamista(Model model, HttpSession session) {
		// for mensaje
		String titulo = "";
		// objeto prestamista en vacio
		Prestamista prestamista = null;
		// Obtener la sesion de quien ingresa
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		// obtener el rol
		int rolIngreso = userLogged.getRol().getIdRol();
		prestamista = new Prestamista();
		// crear un nuevo PersonaM para registrar al darle al boton registrar crea un
		// nuevo objeto PersonaM
		prestamista.setPrestamista(new Persona());

		if (rolIngreso == 1)
			titulo = "Registrar Jefe Prestamista";
		else
			titulo = "Registrar Prestamista";

		model.addAttribute("prestamista", prestamista);

		model.addAttribute("titulo", titulo);

		return "formulario";
	} // fin de mostrarFormularioRegistroPrestamista

	@PostMapping("/registrar") // localhost:9090/registrar
	public String guardarPrestamista(Prestamista prestamista, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status, HttpSession session) {
		if (result.hasErrors()) {

			model.addAttribute("titulo", "Registro");

			return "formulario";
		}
		prestamista.setActivo(true);
		prestamista.getPrestamista().setActivo(true);

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		int rolUsuario = userLogged.getRol().getIdRol();

		String mensaje;
		int idPersona = prestamista.getPrestamista().getIdPersona();
		// if (prestamista.getIdPrestamista() != 0)

		String dni = prestamista.getPrestamista().getDni();

		if (idPersona != 0) {
			service.guardarPrestamista(prestamista);
			return "redirect:/listar";
		}

		else {

			if (service.buscarPorDni(dni) != null) {
				mensaje = "El dni " + dni + " ha sido registrado anteriormente";
				flash.addFlashAttribute("mensaje", mensaje);
				return "redirect:/registrar";
			}

			mensaje = "El Prestamista se registró correctamente";
			Prestamista newPrestamista = service.guardarPrestamista(prestamista); // Marca el status como completo.

			if (rolUsuario == 1) {
				int registrarJefe = 2;
				return "redirect:/registrarUsuario/" + registrarJefe + "/" + newPrestamista.getIdPrestamista();
			} else {
				int idJefePrestamista = userLogged.getPersona().getIdPersona();
				Prestamista jefePrestamista = service.getPrestamistaById(idJefePrestamista).orElse(null);
				// Registrar Asesor Prestamista
				if (idPersona == 0) {
					grupoController.insertGrupoPrestamista(jefePrestamista, newPrestamista, userLogged);
				}
				int registrarAsesor = 3;
				return "redirect:/registrarUsuario/" + registrarAsesor + "/" + newPrestamista.getIdPrestamista();
			}
		}

	} // fin de guardarPrestamista

	// Metodo para actualizar
	@GetMapping("/actualizar/{id}")
	public String editarPrestamista(@PathVariable(name = "id") int id, Model model, RedirectAttributes flash) {
		// creamos objeto presta inicializado en null
		Prestamista presta = null;
		// Valida que el id sea mayor a 0
		if (id > 0) {
			// Si es válido, busca el presta en el servicio por id
			presta = service.listarPrestamistaPorId(id);
			// Valida que existe, si no arroja error
			if (Objects.isNull(presta)) {
				flash.addFlashAttribute("error", "El ID de Prestamista no existe");
				// Retorna un redirect a la URL /listar para mostrar la lista con el atributo
				// error que almacena
				// el mensaje
				return "redirect:/listar";

			} // fin de if
		} else {
			flash.addFlashAttribute("error", "El ID de Prestamista no puede ser menor a 1");
			// Retorna un redirect a la URL /listar para mostrar la lista con el atributo
			// error que almacena
			// el mensaje
			return "redirect:/listar";
		}
		// Si existe, agrega el empleado al modelo
		// Si el empleado existe, la función lo agrega a un objeto llamado empleado
		// y lo pasaremos a la vista por medio del atributo empleado
		model.addAttribute("prestamista", presta);
		// enviamos en atributo llamado titulo, un string que
		// dice Detalle de Empleado concantenado el nombre
		model.addAttribute("titulo", "Actualización");
		// esta vista se usara para el agregar y actualizar reutilizando la vista
		return "formulario";
	} // fin de editarEmpleado

	// Metodo para eliminar
	// Mapea la petición GET a la URL "/eliminar/{id}"
	// Extrae el id de la URL usando @PathVariable
	@GetMapping("/eliminar/{id}")
	public String eliminarPrestamista(@PathVariable(name = "id") int id, RedirectAttributes flash,
			HttpSession session) {
		// Valida que el id sea mayor a 0
		if (id > 0) {
			Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
			int rolIngreso = userLogged.getRol().getIdRol();

			// Como admin - Elimino a un Jefe
			if (rolIngreso == 1) {
				Prestamista jefePrestamista = service.getPrestamistaById(id).orElse(null);
				int totalDeMiembros = grupoController.listGrupoByJefePrestamista(jefePrestamista).size();
				// De ser el caso que no tenga miembros, se podrá eliminar al jefePrestamista
				if (totalDeMiembros < 1)
					service.eliminarPrestamista(id);
				else
					System.out.println("Sry manito no se puede, este jefe es gozu :v");
			}

			// Como Jefe - Elimino a un asesorPrestamista
			else {
				int idJefePrestamista = userLogged.getPersona().getIdPersona();
				Prestamista jefePrestamista = service.getPrestamistaById(idJefePrestamista).orElse(null);
				Prestamista asesorPresmista = service.getPrestamistaById(id).orElse(null);

				// Eliminacion Lógica
				service.eliminarPrestamista(id);
				grupoController.deleteGrupoPrestamista(jefePrestamista, asesorPresmista, userLogged);

				flash.addFlashAttribute("success", "El Prestamista ha sido eliminado");
				return "redirect:/listar";
			}
		} // fin de if
		return "redirect:/listar";
	} // fin de eliminarEmpleado
	
	
	@GetMapping("/aprobarPrestamo")
	private String listar33(Model mode) {
		
		
		return "ApruebaByPrestamista";
	}
	
	
	

} // Fin de PrestamistaController
