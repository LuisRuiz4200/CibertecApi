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

import com.cibertec.api.model.GrupoPrestamista;
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

	//----
	//service
	private PrestamistaService service;
	private GrupoPrestamistaController grupoController;
	private UService userService;

	@GetMapping({"/listar", "/", ""}) //localhost:9090 /
	public String listarPrestamista(Model model, HttpSession session) {
		// Obtener al JefePrestamista desde la session de su Usuario
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		
		//obtener el rol
		int rolIngreso = userLogged.getRol().getIdRol();
		//System.out.println(rolIngreso);
		//Listado declarado
		List<Prestamista> lista = new ArrayList<>();
		//for mensaje
		String titulo = "";
		switch(rolIngreso) {
		//admin lista de jefes
		
		case 1:{
			//obtener id
			int idAdministrador = userLogged.getPersona().getIdPersona();
			
			// Validación correspondiente
			if(idAdministrador == -1)
				return "redirect:/intranet";
			
			/* Listado por Jefes */
			Rol rolJefes = new Rol();
			rolJefes.setIdRol(2);
			List<Usuario> users = userService.getUsuarioByRol(rolJefes);
			lista = users.stream()
			.map(usuario -> service.getPrestamistaById(usuario.getPersona().getIdPersona()).orElse(null)).collect(Collectors.toList());
			/* ================= */
			titulo = "Lista de Jefes de Prestamista";
			break;
		}
		//jefe de prestamista, lista de prestamistas
		case 2:{
			int idJefePrestamista = userLogged.getPersona().getIdPersona();
			Prestamista jefePrestamista = service.getPrestamistaById(idJefePrestamista).orElse(null);
			// Validación correspondiente
			if(jefePrestamista == null)
				return "redirect:/intranet";
			// Obtener la lista de prestamistas asociado al Jefe y que estén Activos
			 lista = grupoController.listGrupoByJefePrestamistaAndActivo(jefePrestamista);
			 titulo = "Lista de Prestamistas";
			 break;
			}
			default: break;
		
		} //fin de switch
		model.addAttribute("lista",lista);
		model.addAttribute("titulo",titulo);
		return "listar";
	} //fin de listarPrestamista


	@GetMapping("/registrar")
	public String mostrarFormularioRegistroPrestamista(Model model, HttpSession session) {
		//for mensaje
				String titulo = "";
				//objeto prestamista en vacio
				Prestamista prestamista=null;
		// Obtener la sesion de quien ingresa
				Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
				//obtener el rol
				int rolIngreso = userLogged.getRol().getIdRol();
				prestamista=new Prestamista();
				//crear un nuevo PersonaM para registrar al darle al boton registrar crea un nuevo objeto PersonaM
				prestamista.setPrestamista(new Persona());
				
				if(rolIngreso == 1) 
					titulo = "Registrar Jefe Prestamista"; 
				else 
					titulo = "Registrar Prestamista";

		model.addAttribute("prestamista",prestamista);
		
		model.addAttribute("titulo",titulo);
		
		return "formulario";
	} //fin de mostrarFormularioRegistroPrestamista
	
	
	@PostMapping("/registrar") //localhost:9090/registrar
	public String guardarPrestamista(Prestamista prestamista,BindingResult result,
			Model model,RedirectAttributes flash,SessionStatus status, HttpSession session) {
		if(result.hasErrors()) {
		
			model.addAttribute("titulo","Registrar Prestamista");
			
			return "formulario";
		}

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		
		int idJefePrestamista = userLogged.getPersona().getIdPersona();
		Prestamista jefePrestamista = service.getPrestamistaById(idJefePrestamista).orElse(null);
		
		String mensaje;
		int idPersona = prestamista.getPrestamista().getIdPersona();
		// if (prestamista.getIdPrestamista() != 0) 
		if(idPersona != 0) 
			mensaje = "El Prestamista se actualizó correctamente"; 
		else 
			mensaje = "El Prestamista se registró correctamente";
		
		Prestamista newPrestamista = service.guardarPrestamista(prestamista); //Marca el status como completo.

		// Registrar en Grupo del Jefe
		GrupoPrestamista grupo = null;
		if(idPersona == 0){
			grupo = grupoController.insertGrupoPrestamista(jefePrestamista, newPrestamista, userLogged);
		}
		if(grupo == null)
			System.out.println("Eres peruano destino a sufrir!");

		status.setComplete();
		
		flash.addFlashAttribute("success", mensaje);
		
		return "redirect:/listar";
		 
	} //fin de guardarPrestamista
	
	//Metodo para actualizar
	@GetMapping("/actualizar/{id}")
	public String editarPrestamista(@PathVariable(name="id") int id,Model model,
			RedirectAttributes flash) {		
		//creamos objeto presta inicializado en null
		Prestamista presta=null;
		//Valida que el id sea mayor a 0
		if(id>0) {
			//Si es válido, busca el presta en el servicio por id
			presta=service.listarPrestamistaPorId(id);
			//Valida que existe, si no arroja error
			if(Objects.isNull(presta)) {				
				flash.addFlashAttribute("error","El ID de Prestamista no existe");
				//Retorna un redirect a la URL /listar para mostrar la lista con el atributo error que almacena
				//el mensaje
				return "redirect:/listar";
				
			} //fin de if
		}
		else {
			flash.addFlashAttribute("error","El ID de Prestamista no puede ser menor a 1");
			//Retorna un redirect a la URL /listar para mostrar la lista con el atributo error que almacena
			//el mensaje
			return "redirect:/listar";
		}
		//Si existe, agrega el empleado al modelo
		// Si el empleado existe, la función lo agrega a un objeto llamado empleado
		//y lo pasaremos a la vista por medio del atributo empleado
		model.addAttribute("prestamista", presta);
		//enviamos en atributo llamado titulo, un string que
		//dice Detalle de Empleado concantenado el nombre
		model.addAttribute("titulo","Edición del Empleado");
		//esta vista se usara para el agregar y actualizar reutilizando la vista
		return "formulario";
	} //fin de editarEmpleado
	
	//Metodo para eliminar
	//Mapea la petición GET a la URL "/eliminar/{id}"
	//Extrae el id de la URL usando @PathVariable
	@GetMapping("/eliminar/{id}")
	public String eliminarPrestamista(@PathVariable(name="id") int id,
				RedirectAttributes flash) {	
	//Valida que el id sea mayor a 0
			if(id>0) {
				//Si es válido, llama al método eliminarPrestamista del servicio, pasándole el id
				service.eliminarPrestamista(id);
				//Agrega un mensaje "flash" de éxito indicando que se eliminó
					flash.addFlashAttribute("success","El Prestamista ha sido eliminado");
					//Retorna un redirect a la URL /listar para mostrar la lista con el atributo success que almacena
					//el mensaje
					return "redirect:/listar";
					
				} //fin de if
			return "redirect:/listar";
		} //fin de eliminarEmpleado
	
	
	
	
	
	
} //Fin de PrestamistaController
