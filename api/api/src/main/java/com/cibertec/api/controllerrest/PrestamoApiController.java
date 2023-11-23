package com.cibertec.api.controllerrest;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
	public  Map<?, ?> guardarPrestamo(@RequestBody Prestamo prestamo) {
		
		Map<String, Object> response = new HashMap<>();
		
		/*campos de las cuotas del prestamo*/
		int cuotas = 0;
		double montoMensual = 0;
		double interes = 0;
		double montoTotal = 0;
		
		try {
			
			prestamo.setActivo(true);
			prestamo.setFechaRegistro(new Date(new java.util.Date().getTime()));
			
			cuotas = prestamo.getCuotas();
			montoMensual = prestamo.getMonto() / prestamo.getCuotas();
			interes = montoMensual * prestamo.getTem();
			montoTotal = montoMensual + interes;
			
			
			prestamo = prestamoService.guardar(prestamo);
			
			Date fechaPago = new Date(new java.util.Date().getTime()); // Fecha actual
		
			
			if(prestamo!=null) {
				
				for (int i = 0 ;i<cuotas;i++) {

					CuotaPrestamo cuotaPrestamo = new CuotaPrestamo();
					
					cuotaPrestamo.getCuotaPrestamoPk().getPrestamo().setIdPrestamo(prestamo.getIdPrestamo());
					cuotaPrestamo.getCuotaPrestamoPk().setIdCuotaPrestamo(i+1);
					cuotaPrestamo.setMonto(montoMensual);
					cuotaPrestamo.setInteres(interes);
					cuotaPrestamo.setMontoTotal(montoTotal);
					
					
					cuotaPrestamo.setEstado("Por pagar");
					
					cuotaPrestamo = cuotaPrestamoService.guardar(cuotaPrestamo);
					
					// Aumentar la fecha para el siguiente mes
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(fechaPago);
                    calendar.add(Calendar.MONTH, 1);
                    fechaPago = new Date(calendar.getTimeInMillis());
                    
                    cuotaPrestamo.setFechaPago(fechaPago);
					
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
