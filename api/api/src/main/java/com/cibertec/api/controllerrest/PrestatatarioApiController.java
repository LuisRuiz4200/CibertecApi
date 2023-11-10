package com.cibertec.api.controllerrest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.Prestatario;
import com.cibertec.api.service.PrestatarioService;

@RestController
@RequestMapping("/api/prestatario")
public class PrestatatarioApiController {

	@Autowired
	PrestatarioService prestatarioService ;
	
	@GetMapping("/listar")
	private ResponseEntity<List<Prestatario>> listar() {
		List<Prestatario> response= prestatarioService.listarPrestatario();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/registrar")
	@ResponseBody
	private String registrar(@RequestBody Prestatario model) {
		
		String response = "";
		
		try {
			model = prestatarioService.guardarPrestatario(model);
			response = "Se ha registrado el ID " + model.getIdPrestatario() + ".";
		}catch(Exception ex){
			response = ex.getMessage();
		}
		
		return response;
	}
	
	@PutMapping("/actualizar")
	@ResponseBody
	private Map<?, ?> actualizar(@RequestBody Prestatario model) {
		
		Map<String, Object> response = new HashMap<String,Object>();
		
		try {
			model = prestatarioService.guardarPrestatario(model);
			response.put("mensaje", "Se ha actualizado el ID " + model.getIdPrestatario() + " y el ID persona " + model.getPrestatario().getIdPersona() + " / " + model.getPrestatario().getNombres());
		}catch(Exception ex){
			response.put("mensaje",ex.getMessage());
		}
		
		return response;
	}
	
}
