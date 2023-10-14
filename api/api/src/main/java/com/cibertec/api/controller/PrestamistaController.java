package com.cibertec.api.controller;
import java.util.List;
import java.util.Objects;

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
import com.cibertec.api.service.PrestamistaService;

import lombok.AllArgsConstructor;
@Controller
@AllArgsConstructor
public class PrestamistaController {

	//----
	//service
	private PrestamistaService service;
	@GetMapping({"/listar", "/", ""}) //localhost:9090 /
	public String listarPrestamista(Model model) {
		
		List<Prestamista> lista =service.listarPrestamista();
	
		model.addAttribute("lista",lista);
	
		model.addAttribute("titulo","Lista de Prestamista");
		
		return "listar";
	} //fin de listarPrestamista

	@GetMapping("/registrar")
	public String mostrarFormularioRegistroPrestamista(Model model) {
	
		Prestamista prestamista=new Prestamista();
		//crear un nuevo PersonaM para registrar al darle al boton registrar crea un nuevo objeto PersonaM
		prestamista.setPrestamista(new Persona());
		
		model.addAttribute("prestamista",prestamista);
		
		model.addAttribute("titulo","Registrar Prestamista");
		
		return "formulario";
	} //fin de mostrarFormularioRegistroPrestamista
	
	
	@PostMapping("/registrar") //localhost:9090/registrar
	public String guardarPrestamista(Prestamista prestamista,BindingResult result,
			Model model,RedirectAttributes flash,SessionStatus status) {
		
		if(result.hasErrors()) {
		
			model.addAttribute("titulo","Registrar Prestamista");
			
			return "formulario";
		}

		
		
		
		
		
		  String mensaje;
		  
		  // if (prestamista.getIdPrestamista() != 0) 
		  if(prestamista.getPrestamista().getIdPersona() != 0) mensaje =
		  "El Prestamista se actualizó correctamente"; else mensaje =
		  "El Prestamista se registró correctamente";
		  
		  service.guardarPrestamista(prestamista); //Marca el status como completo.
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
	
	
	
	
	
	
	
	
} //Fin de PrestamistaController
