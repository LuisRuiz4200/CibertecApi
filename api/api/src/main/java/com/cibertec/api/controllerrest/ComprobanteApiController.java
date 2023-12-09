package com.cibertec.api.controllerrest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.tomcat.util.json.JSONParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.Comprobante;
import com.cibertec.api.model.ComprobanteDetalle;
import com.cibertec.api.model.CuotaPrestamo;
import com.cibertec.api.model.Prestamo;
import com.cibertec.api.modelDTO.ComprobanteDTO;
import com.cibertec.api.modelDTO.ComprobanteDetalleDTO;
import com.cibertec.api.reuzable.Utils;
import com.cibertec.api.service.ComprobanteDetalleService;
import com.cibertec.api.service.ComprobanteService;
import com.cibertec.api.service.CuotaPrestamoService;
import com.cibertec.api.service.PrestamoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/api/comprobante")
public class ComprobanteApiController {

	@Autowired
	ComprobanteService comprobanteService;
	@Autowired
	ComprobanteDetalleService comprobanteDetalleService;
	@Autowired
	CuotaPrestamoService cuotaPrestamoService;
	
	
	@PostMapping("/registrar")
	private Map<String, Object> registrar(@RequestBody ComprobanteDTO comprobanteDTO){
		
		Map<String,Object> response = new HashMap<>();
		Map<String,Object> detalle = new HashMap<>();
		
		ModelMapper modelMapper = new ModelMapper();
		
		Comprobante comprobante = new Comprobante();
		ComprobanteDetalle comprobanteDetalle = new ComprobanteDetalle();
		CuotaPrestamo cuotaPrestamo = new CuotaPrestamo();
		
		Integer correlativo = 1;
		String serie = "";
		
		try {

			comprobante = modelMapper.map(comprobanteDTO, Comprobante.class);
			
			correlativo = comprobanteService.listar().stream()
					.map(c->c.getCorrelativo())
					.max(Comparator.naturalOrder()).orElse(0);
			
			
			comprobante.setCorrelativo(correlativo + 1);
			comprobante = comprobanteService.guardar(comprobante);
			
			correlativo = comprobante.getCorrelativo();
			serie = comprobante.getSerie();
			
			for (ComprobanteDetalleDTO cpeDTO : comprobanteDTO.getListaComprobanteDetalle()) {
				
				comprobanteDetalle = new ComprobanteDetalle();
				
				comprobanteDetalle.getComprobanteDetallePK().setIdComprobante(comprobante.getIdComprobante());
				comprobanteDetalle.getComprobanteDetallePK().setIdComprobanteDetalle(cpeDTO.getIdComprobanteDetalle());
				
				comprobanteDetalle.getCuotaPrestamo().getCuotaPrestamoPk().setIdPrestamo(comprobante.getPrestamo().getIdPrestamo());
				comprobanteDetalle.getCuotaPrestamo().getCuotaPrestamoPk().setIdCuotaPrestamo(cpeDTO.getIdCuotaPrestamo());
				
				comprobanteDetalle.setCodItem(cpeDTO.getCodItem());
				comprobanteDetalle.setDescripcion(cpeDTO.getDescripcion());
				comprobanteDetalle.setCantidadItem(cpeDTO.getCantidadItem());
				comprobanteDetalle.setMontoItem(cpeDTO.getMontoItem());
				comprobanteDetalle.setMontoTotal(cpeDTO.getMontoTotal());
				
				comprobanteDetalle = comprobanteDetalleService.guardar(comprobanteDetalle);
				
				cuotaPrestamo = comprobanteDetalle.getCuotaPrestamo();

				

				double montoPendienteActual = cuotaPrestamo.getMontoPendiente();
				double montoFacturado = comprobanteDetalle.getMontoTotal();
				
				if(montoPendienteActual == 0) {
					montoPendienteActual = cuotaPrestamo.getMontoTotal();
				}
				

				if(comprobanteDetalle.getMontoTotal()>=montoPendienteActual) {
					cuotaPrestamo.setEstado(Utils.PAGO_PAGADO);
				}else {
					cuotaPrestamo.setEstado(Utils.PAGO_PARCIAL);
				}
				
				cuotaPrestamo.setMontoPendiente(montoPendienteActual - montoFacturado);
				
				cuotaPrestamo = cuotaPrestamoService.guardar(cuotaPrestamo);
				
			}
			
			
			if(comprobante.getIdComprobante()<0) {
				response.put("error", "Error durante el proceso");
				return response;
			}

			detalle.put("serie",serie);
			detalle.put("correlativo", correlativo);

			response.put("mensaje", "Comprobante registrado !");
			response.put("detalle", detalle);
			
			
		}catch(Exception ex) {
			response.put("error", ex.getMessage());
		}
		
		return response;
	}
	
	
	
	@GetMapping("/listar")
	private List<Comprobante> listar(
			@RequestParam(name="idPrestamo",required = false,defaultValue = "0")int idPrestamo,
			@RequestParam(name="idCuotaPrestamo",required = false,defaultValue = "0")int idCuotaPrestamo){
		
		List<Comprobante> listaComprobante = new ArrayList<>();
		
		try {
			
			listaComprobante = comprobanteService.listar();
			
			if (idPrestamo>0 && idCuotaPrestamo>0) {
				listaComprobante = comprobanteService.listar().stream()
						.filter(c->c.getPrestamo().getIdPrestamo()==idPrestamo)
						.peek(c->c.setListaComprobanteDetalle(
								c.getListaComprobanteDetalle().stream()
								.filter(detalle->detalle.getCuotaPrestamo().getCuotaPrestamoPk().getIdCuotaPrestamo()==idCuotaPrestamo)
								.toList()
								))
						.toList();
			}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return listaComprobante;
	}
	
}
