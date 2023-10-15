package com.cibertec.api.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.api.model.Menu;
import com.cibertec.api.model.Persona;
import com.cibertec.api.model.Rol;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.PersonaService;
import com.cibertec.api.service.UService;
import com.cibertec.api.service.RolService;
import com.cibertec.api.service.UsuarioService;

import lombok.AllArgsConstructor;

//creamos atributo de tipo sesion con sessionatributes
//donde ENLACES y USUARIO son atributo de tipo sesion
@SessionAttributes({"ENLACES","USUARIO"})
@AllArgsConstructor
@Controller
@AllArgsConstructor
public class UsuarioController {
//para crud de usuario
	private usuarioService serviceUsuario;
	//para llamar al list de rol para combo
	private rolService serviceRol;
	//para llamar al list de persona para combo
	private personaService servicePersona; 
	
	@GetMapping("/listarUsuario") //localhost:9090 /
	public String listarUsuario(Model model) {
		
		List<tbusuario> lista =serviceUsuario.listarUsuario();
	
	@RequestMapping("/intranet")
	public String intranet(Authentication  auth,Model model){
		String vLogin=auth.getName();
		 Usuario u=servicio.loginUsuario(vLogin);
		 List<Menu> lista=servicio.enlacesDelUsuario(u.getRol().getIdRol());
		 
	     model.addAttribute("ENLACES",lista);
	     
			//retornamos la pagina o vista intranet.html
		return "intranet";		
	}
	
	
	
    //Codigo Ingresado 
	
	//para crud de usuario
		private UService serviceUsuario;
		//para llamar al list de rol para combo
		private rolService serviceRol;
		//para llamar al list de persona para combo
		private PersonaService servicePersona; 
		
		@GetMapping("/listarUsuario") //localhost:9090 /
		public String listarUsuario(Model model) {
			
			List<Usuario> lista =serviceUsuario.listarUsuario();
		
			model.addAttribute("lista",lista);
		
			model.addAttribute("titulo","Lista de Usuarios");
			
			return "listarUsuario";
		} //fin de listarUsuario
		
		@GetMapping("/registrarUsuario")
		public String mostrarFormularioRegistroUsuario(Model model) {
			//creamos objeto usuario vacio
			Usuario usuario=new Usuario();
			//creamos listado para rol para combo
			List<Rol> dataRol=serviceRol.listarRol();
			//creamos listado para persona para combo
			List<Persona> dataPersona=servicePersona.listarPersona();
			//crear un nuevo PersonaM para registrar al darle al boton registrar crea un nuevo objeto PersonaM
			//usuario.setPrestamista  (new PersonaM());
			
			model.addAttribute("usuario",usuario);
			//pasamos para los combobox
			model.addAttribute("comboRol",dataRol);
			model.addAttribute("comboPersona",dataPersona);
			//
			model.addAttribute("titulo","Registrar Usuario");
			//asi se llamara el html
			return "usuarioRegistro";
		} //fin de mostrarFormularioRegistroPrestamista
		
		
		@PostMapping("/registrarUsuario") //localhost:9090/registrarUsuario
		//public String guardarUsuario(@Valid tbusuario usuario,BindingResult result,
		public String guardarUsuario(Usuario usuario,BindingResult result,
				Model model,RedirectAttributes flash,SessionStatus status) {
			if(result.hasErrors()) {
				model.addAttribute("titulo","Registrar Usuario");
				return "usuarioRegistro";
			}
			  String mensaje; 
			  // if (usuario.getIdPrestamista() != 0) 
			  if(usuario.getIdUsuario() != 0) 
			   mensaje =
			  "El Prestamista se actualizó correctamente"; 
			  else mensaje =
			  "El Prestamista se registró correctamente";
			  
			  serviceUsuario.guardarUsuario(usuario); 
			  //Marca el status como completo.
			  status.setComplete();
			  flash.addFlashAttribute("success", mensaje);
			  //redireccionamos
			  return "redirect:/listarUsuario";
		} //fin de guardarUsuario
		
		
		//Metodo para actualizar
			@GetMapping("/actualizarUsuario/{id}")
			public String editarPrestamista(@PathVariable(name="id") int id,Model model,
					RedirectAttributes flash) {		
				//creamos objeto presta inicializado en null
				Usuario usu=null;
				//Valida que el id sea mayor a 0
				if(id>0) {
					//Si es válido, busca el usu en el servicio por id
					usu=serviceUsuario.listarUsuarioPorId(id);
					
					//Valida que existe, si no arroja error
					if(Objects.isNull(usu)) {				
						flash.addFlashAttribute("error","El ID de USUARIO no existe");
						//Retorna un redirect a la URL /listar para mostrar la lista con el atributo error que almacena
						//el mensaje
						return "redirect:/listarUsuario";
						
					} //fin de if
				}
				else {
					flash.addFlashAttribute("error","El ID de Usuario no puede ser menor a 1");
					//Retorna un redirect a la URL /listarUsuario para mostrar la lista con el atributo error que almacena
					//el mensaje
					return "redirect:/listarUsuario";
				}
				//Si existe, agrega el usuario al modelo
				// Si el usu existe, la función lo agrega a un objeto llamado usu
				//y lo pasaremos a la vista por medio del atributo usuario
				model.addAttribute("usuario", usu);
				//------------------------------------------- PARA COMBOBOX al actualizar
				//creamos listado para rol para combo
				List<Rol> dataRol=serviceRol.listarRol();
				//creamos listado para persona para combo
				List<Persona> dataPersona=servicePersona.listarPersona();
				//pasamos para  llenar los combobox
				model.addAttribute("comboRol",dataRol);
				model.addAttribute("comboPersona",dataPersona);
				//Enviamos el id para que el propio combobox lo seleccione
				//Si es válido, busca el usu en el servicio por id
				usu=serviceUsuario.listarUsuarioPorId(id);
				//obtenemos el id de del usuario buscado y lo asignamos a estos atributos
				//RolId y PersonaId
				model.addAttribute("RolId",usu.getRol().getIdRol());
				model.addAttribute("PersonaId",usu.getPersona().getIdPersona());			
				//--------------------------------------------
				
				//enviamos en atributo llamado titulo, un string que
				//dice Detalle de Empleado concantenado el nombre
				model.addAttribute("titulo","Actualización del Empleado");
				//esta vista se usara para el agregar y actualizar reutilizando la vista
				return "usuarioRegistro";
			} //fin de editarEmpleado
			
			//Metodo para eliminar
			//Mapea la petición GET a la URL "/eliminarUsuario/{id}"
			//Extrae el id de la URL usando @PathVariable
			@GetMapping("/eliminarUsuario/{id}")
			public String eliminarPrestamista(@PathVariable(name="id") int id,
					RedirectAttributes flash) {	
				//Valida que el id sea mayor a 0
				if(id>0) {
					//Si es válido, llama al método eliminarPrestamista del servicio, pasándole el id
					serviceUsuario.eliminarUsuario(id);
					//Agrega un mensaje "flash" de éxito indicando que se eliminó
						flash.addFlashAttribute("success","El Usuario ha sido eliminado");
						//Retorna un redirect a la URL /listar para mostrar la lista con el atributo success que almacena
						//el mensaje
						return "redirect:/listarUsuario";
						
					} //fin de if
				return "redirect:/listarUsuario";
			} //fin de eliminarEmpleado
			

}




