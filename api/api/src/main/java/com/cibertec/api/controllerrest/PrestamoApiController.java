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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.CuotaPrestamo;
import com.cibertec.api.model.CuotaPrestamoPK;
import com.cibertec.api.model.Prestamo;
import com.cibertec.api.reuzable.Utils;
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
					.filter(c -> c.getPrestamo().getSolicitudPrestamo().getPrestatario().getPrestatario()
							.getIdPersona() == idPrestatario)
					.filter(c -> c.getFechaPago().after(fechaInicioCuota) && c.getFechaPago().before(fechaFinCuota))
					.sorted(Comparator.comparing(CuotaPrestamo::getFechaPago)).toList();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listaCuotaPrestamos;
	}
	
	@GetMapping("/buscar/cuotaPrestamo")
	@ResponseBody
	public CuotaPrestamo buscarCuotaPrestamo(
			@RequestParam(name="idPrestamo",required = false) Integer idPrestamo, 
			@RequestParam(name="idCuotaPrestamo",required = false) Integer idCuotaPrestamo) {
		
		CuotaPrestamo cuotaPrestamo = new CuotaPrestamo();
		
		try {
			
			CuotaPrestamoPK cuotaPrestamoPK = new CuotaPrestamoPK();
			cuotaPrestamoPK.setIdPrestamo(idPrestamo);
			cuotaPrestamoPK.setIdCuotaPrestamo(idCuotaPrestamo);
			
			cuotaPrestamo = cuotaPrestamoService.buscarPorIdCompuesto(cuotaPrestamoPK);
			
		}catch(Exception ex){
			ex.printStackTrace();
		};
		
		
		return cuotaPrestamo;
	}

	//Jeanpi
	@GetMapping("/listar")
	public HashMap<String, Object> listar(
		@RequestParam(name = "idPrestamista", required = false, defaultValue = "0") int idPrestamista) {

		List<Prestamo> listaPrestamo = new ArrayList<>();
		HashMap<String, Object> res = new HashMap<>();
		HashMap<String, Object> extraInfo = new HashMap<>();
		HashMap<String, Object> resumen = new HashMap<>();
		List<HashMap<String, Object>> prestamos = new ArrayList<>();
		
		try {
			listaPrestamo = prestamoService.listar();
			if (idPrestamista > 0) {
				listaPrestamo = prestamoService.listar().stream().filter(c -> c.getSolicitudPrestamo().getPrestatario()
						.getPrestamistaPrestatario().getPrestamista().getIdPersona() == idPrestamista).toList();
			}
			int cuotaPorPagar = 0;
			int cuotaPagadas = 0;
			double montoPagado = 0;
			double montoPorPagar = 0;

			for (Prestamo prestamo : listaPrestamo) {	
				cuotaPorPagar = prestamo.getListaCuotaPrestamo().stream().filter(
						c -> c.getEstado().matches("(" + Utils.PAGO_PENDIENTE + ")||(" + Utils.PAGO_PARCIAL + ")"))
						.toList().size();

				cuotaPagadas = prestamo.getListaCuotaPrestamo().stream()
						.filter(c -> c.getEstado().matches("(" + Utils.PAGO_PAGADO + ")")).toList().size();
				montoPagado = 0;
				montoPorPagar = 0;
				
				for (CuotaPrestamo cuotaPrestamo : prestamo.getListaCuotaPrestamo()) {
					if (cuotaPrestamo.getEstado()
							.matches("(" + Utils.PAGO_PENDIENTE + ")||(" + Utils.PAGO_PARCIAL + ")")) {
						montoPorPagar += cuotaPrestamo.getMontoTotal();
					}
					if (cuotaPrestamo.getEstado()
							.matches("(" + Utils.PAGO_PAGADO + ")")) {
						montoPagado += cuotaPrestamo.getMontoTotal();
					}	
				}
				extraInfo = new HashMap<>();
				extraInfo.put("idPrestamo",prestamo.getIdPrestamo());
				extraInfo.put("nomPrestatario",prestamo.getSolicitudPrestamo().getPrestatario().getPrestatario().getNombres());
				extraInfo.put("apePrestatario",prestamo.getSolicitudPrestamo().getPrestatario().getPrestatario().getApellidos());
				extraInfo.put("montoTotal",prestamo.getMonto() * (prestamo.getTem() + 1));
				extraInfo.put("cuotas",prestamo.getCuotas());
				extraInfo.put("cuotaPorPagar", cuotaPorPagar);
				extraInfo.put("cuotaPagadas", cuotaPagadas);
				extraInfo.put("montoPorPagar", montoPorPagar);
				extraInfo.put("montoPagado", montoPagado);
				prestamos.add(extraInfo);
			}	
			
			resumen.put("totalPorPagar", cuotaPorPagar);
			resumen.put("totalPagadas", cuotaPagadas);
			resumen.put("montoPorPagar", montoPorPagar);
			resumen.put("montoPagado", montoPagado);
			res.put("resumen", resumen);
			res.put("prestamos", prestamos); 

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return res;
	}

	@PostMapping("/guardarPrestamo")
	@Transactional
	public Map<?, ?> guardarPrestamo(@RequestBody Prestamo prestamo) {

		Map<String, Object> response = new HashMap<>();

		/* campos de las cuotas del prestamo */
		int cuotas = 0;
		double montoMensual = 0;
		double interes = 0;
		double montoTotal = 0;

		try {

			Date fechaActual = new Date(new java.util.Date().getTime());
			
			prestamo.setActivo(true);
			prestamo.setFechaRegistro(fechaActual);

			cuotas = prestamo.getCuotas();
			montoMensual = prestamo.getMonto() / prestamo.getCuotas();
			interes = montoMensual * prestamo.getTem();
			montoTotal = montoMensual + interes;

			prestamo = prestamoService.guardar(prestamo);

			if (prestamo != null) {

				for (int i = 0; i < cuotas; i++) {

					CuotaPrestamo cuotaPrestamo = new CuotaPrestamo();

					cuotaPrestamo.getCuotaPrestamoPk().setIdPrestamo(prestamo.getIdPrestamo());
					cuotaPrestamo.getCuotaPrestamoPk().setIdCuotaPrestamo(i + 1);
					cuotaPrestamo.setMonto(montoMensual);
					cuotaPrestamo.setInteres(interes);
					cuotaPrestamo.setMontoTotal(montoTotal);
					cuotaPrestamo.setMontoPendiente(montoTotal);
					cuotaPrestamo.setEstado(Utils.PAGO_PENDIENTE);
					
					cuotaPrestamo = cuotaPrestamoService.guardar(cuotaPrestamo);

					// Aumentar la fecha para el siguiente mes
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(fechaActual);
					calendar.add(Calendar.MONTH, 1);
					Date nuevaFecha = new Date(calendar.getTimeInMillis());

					cuotaPrestamo.setFechaPago(nuevaFecha);

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
