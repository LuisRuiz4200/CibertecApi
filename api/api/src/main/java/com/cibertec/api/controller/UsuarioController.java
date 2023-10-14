package com.cibertec.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import java.util.List;

import com.cibertec.api.model.Menu;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.UsuarioService;


//creamos atributo de tipo sesion con sessionatributes
//donde ENLACES y USUARIO son atributo de tipo sesion
@SessionAttributes({"ENLACES","USUARIO"})

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService servicio;
	
	@RequestMapping("/login")
	public String login(){
		//Si ponemos en la ruta localhost:9090/login nos redirecciona AQUI 
		return "inicio";
	}
	
	@RequestMapping("/intranet")
	public String intranet(Authentication  auth,Model model){
		String vLogin=auth.getName();
		 Usuario u=servicio.loginUsuario(vLogin);
		 List<Menu> lista=servicio.enlacesDelUsuario(u.getRol().getIdRol());
		 
	     model.addAttribute("ENLACES",lista);
	     
			//retornamos la pagina o vista intranet.html
		return "intranet";		
	
	}
}
