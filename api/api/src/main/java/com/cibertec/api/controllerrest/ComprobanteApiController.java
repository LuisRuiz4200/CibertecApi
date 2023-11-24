package com.cibertec.api.controllerrest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.Comprobante;
import com.cibertec.api.model.ComprobanteDetalle;
import com.cibertec.api.modelDTO.ComprobanteDTO;
import com.cibertec.api.modelDTO.ComprobanteDetalleDTO;
import com.cibertec.api.service.ComprobanteDetalleService;
import com.cibertec.api.service.ComprobanteService;

@RestController
@RequestMapping("/api/comprobante")
public class ComprobanteApiController {

	@Autowired
	ComprobanteService comprobanteService;
	@Autowired
	ComprobanteDetalleService comprobanteDetalleService;
	
	@PostMapping("/registrar")
	private Map<String, Object> registrar(@RequestBody ComprobanteDTO comprobanteDTO){
		
		Map<String,Object> response = new HashMap<>();
		ModelMapper modelMapper = new ModelMapper();
		
		Comprobante comprobante = new Comprobante();
		ComprobanteDetalle comprobanteDetalle = new ComprobanteDetalle();
		
		
		try {

			comprobante = modelMapper.map(comprobanteDTO, Comprobante.class);
			comprobante = comprobanteService.guardar(comprobante);
			
			int idDetalleComprobante = 1;
			
			for (ComprobanteDetalleDTO cpeDTO : comprobanteDTO.getListaComprobanteDetalle()) {
				comprobanteDetalle = modelMapper.map(cpeDTO, ComprobanteDetalle.class);
				comprobanteDetalle.getComprobanteDetallePK().setIdComprobante(comprobante.getIdComprobante());
				comprobanteDetalle.getComprobanteDetallePK().setIdComprobanteDetalle(idDetalleComprobante ++);
				comprobanteDetalle = comprobanteDetalleService.guardar(comprobanteDetalle);
			}
			
			
			if(comprobante.getIdComprobante()<0) {
				response.put("error", "Error durante el proceso");
				return response;
			}

			response.put("mensaje", "Trabajo realizado");
			
		}catch(Exception ex) {
			response.put("error", ex.getMessage());
		}
		
		return response;
	}
	
	@GetMapping("/listar")
	private List<Comprobante> listar(){
		
		List<Comprobante> listaComprobante = new ArrayList<>();
		
		
		try {
			
			listaComprobante = comprobanteService.listar();
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaComprobante;
	}
	
}
