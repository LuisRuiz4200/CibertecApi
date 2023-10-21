package com.cibertec.api.controllerrest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.Prestamista;
import com.cibertec.api.service.PrestamistaService;

@RestController
@RequestMapping("/api/prestamista")
public class PrestamistaApiController {

	@Autowired
	PrestamistaService prestamistaService ;
	
	@GetMapping("/listar")
	private ResponseEntity<List<Prestamista>> listar() {
		List<Prestamista> response= prestamistaService.listarPrestamista();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/registrar")
	private String registrar(@RequestBody Prestamista model) {
		
		String response = "";
		
		try {
			model = prestamistaService.guardarPrestamista(model);
			response = "Se ha registrado el ID " + model.getIdPrestamista() + ".";
		}catch(Exception ex){
			response = ex.getMessage();
		}
		
		return response;
	}
	
	@PutMapping("/actualizar")
	private Map<?, ?> actualizar(@RequestBody Prestamista model) {
		
		Map<String, Object> response = new HashMap<String,Object>();
		
		try {
			model = prestamistaService.guardarPrestamista(model);
			response.put("mensaje", "Se ha actualizado el ID " + model.getIdPrestamista() + " y el ID persona " + model.getPrestamista().getIdPersona() + " / " + model.getPrestamista().getNombres());
		}catch(Exception ex){
			response.put("mensaje",ex.getMessage());
		}
		
		return response;
	}
	
	@GetMapping("/validarDniExiste/{dni}")
	private Map<?,?> validarDniExiste(@PathVariable String dni){
		
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			if (prestamistaService.buscarPorDni( dni)!=null) {
				response.put("mensaje", "El dni " + dni + " ya existe");
			}
		}catch(Exception ex) {
			response.put("error", ex.getMessage() );
		}
		
		return response;
	}
	@GetMapping("/validarRucExiste/{ruc}")
	private Map<?,?> validarRucExiste(@PathVariable String ruc){
		
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			if(prestamistaService.buscarPorRuc(ruc)!=null) {
				response.put("mensaje", "El ruc " + ruc + " ya existe");
			}
			
		}catch(Exception ex) {
			response.put("error", ex.getMessage() );
		}
		
		return response;
	}
	
}
