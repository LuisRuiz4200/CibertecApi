package com.cibertec.api.controllerrest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.CuotaPrestamo;
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

	@GetMapping("/listaCuotaPorPrestatario")
	public List<CuotaPrestamo> listarPrestamosPorPrestatario(
			@RequestParam(name = "idPrestatario", required = false, defaultValue = "") int idPrestatario,
			@RequestParam(name = "fechaInicioCuota", required = false, defaultValue = "1999-01-01") Date fechaInicioCuota,
			@RequestParam(name = "fechaFinCuota", required = false, defaultValue = "9999-01-01") Date fechaFinCuota) {

		List<CuotaPrestamo> listaCuotaPrestamos = new ArrayList<>();

		try {

			listaCuotaPrestamos = cuotaPrestamoService.listar().stream()
					.filter(c -> c.getPrestamo().getSolicitudPrestamo().getPrestatario().getPrestatario().getIdPersona() == idPrestatario)
					.filter(c->c.getFechaPago().after(fechaInicioCuota)&&c.getFechaPago().before(fechaFinCuota))
					.sorted(Comparator.comparing(CuotaPrestamo::getFechaPago))
					.toList();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listaCuotaPrestamos;
	}
	
	@GetMapping("/listar")
	public List<Prestamo> listarPrestamosPorPrestatario() {

		List<Prestamo> listaPrestamo = new ArrayList<>();

		try {

			listaPrestamo = prestamoService.listar();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listaPrestamo;
	}
	

	@PostMapping("/guardarPrestamo")
	@ResponseBody
	@Transactional
	public Map<?, ?> guardarPrestamo(@RequestBody Prestamo prestamo) {

		Map<String, Object> response = new HashMap<>();

		/* campos de las cuotas del prestamo */
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

			if (prestamo != null) {

				for (int i = 0; i < cuotas; i++) {

					CuotaPrestamo cuotaPrestamo = new CuotaPrestamo();

					cuotaPrestamo.getCuotaPrestamoPk().setIdPrestamo(prestamo.getIdPrestamo());
					cuotaPrestamo.getCuotaPrestamoPk().setIdCuotaPrestamo(i + 1);
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

			} else {
				response.put("mensaje", "Error durante la transacción");
			}

		} catch (Exception ex) {
			response.put("error", ex.getMessage());
		}

		return response;
	}

}
