package com.cibertec.api.controllerrest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.CuotaPrestamo;
import com.cibertec.api.model.CuotaPrestamoPK;
import com.cibertec.api.model.Prestamo;
import com.cibertec.api.service.CuotaPrestamoService;
import com.cibertec.api.service.PrestamoService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/prestamo")
public class PrestamoApiController {
	
	@Autowired
	PrestamoService prestamoService;
	@Autowired
	CuotaPrestamoService cuotaPrestamoService;
	
	@PostMapping("/guardarPrestamo")
	@ResponseBody
	@Transactional
	private  Map<?, ?> guardarPrestamo(@RequestBody Prestamo prestamo) {
		
		Map<String, Object> response = new HashMap<>();
		
		/*campos de las cuotas del prestamo*/
		int cuotas = 0;
		double montoMensual = 0;
		double interes = 0;
		double montoTotal = 0;
		
		try {
			
			cuotas = prestamo.getCuotas();
			montoMensual = prestamo.getMonto() / prestamo.getCuotas();
			interes = montoMensual * prestamo.getTem();
			montoTotal = montoMensual + interes;
			
			prestamo = prestamoService.guardar(prestamo);
			
			
			
			if(prestamo!=null) {
				
				for (int i = 0 ;i<cuotas;i++) {

					CuotaPrestamo cuotaPrestamo = new CuotaPrestamo();
					
					cuotaPrestamo.getCuotaPrestamoPk().setIdPrestamo(prestamo.getIdPrestamo());
					cuotaPrestamo.getCuotaPrestamoPk().setIdCuotaPrestamo(i+1);
					cuotaPrestamo.setMonto(montoMensual);
					cuotaPrestamo.setInteres(interes);
					cuotaPrestamo.setMontoTotal(montoTotal);
					cuotaPrestamo.setEstado("Por pagar");
					
					cuotaPrestamo = cuotaPrestamoService.guardar(cuotaPrestamo);
					
				}
				
				response.put("mensaje", "Prestamo generado");
				
			}else {
				response.put("mensaje", "Error durante la transacción");
			}
			
			
		}catch(Exception ex) {
			response.put("error", ex.getMessage());
		}
		
		return response;
	}
	

}
