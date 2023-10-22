package com.cibertec.api.controllerrest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.UService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioApiController {

	@Autowired
	UService uService;

	@GetMapping("/validarUsuarioExiste/{nombreUsuario}")
	@ResponseBody
	private Map<?, ?> validarUsuarioExiste(@PathVariable String nombreUsuario) {

		Map<String, Object> response = new HashMap<>();

		try {
			Usuario usuario = uService.buscarPorNombreUsuario(nombreUsuario);
			if (usuario != null) {
				response.put("mensaje", "El nombre de usuario " + nombreUsuario + " ya se encuenta en uso");
			}

		} catch (Exception ex) {
			response.put("error", ex.getMessage());
		}
		return response;
	}

}
