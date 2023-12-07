package com.cibertec.api.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibertec.api.model.CuotaPrestamo;
import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Prestamo;
import com.cibertec.api.model.PrestamoDto;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.reuzable.Utils;
import com.cibertec.api.service.CuotaPrestamoService;
import com.cibertec.api.service.GrupoPrestamistaService;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestamoService;
import com.cibertec.api.service.PrestatarioService;
import com.cibertec.api.service.SolicitudPrestamoService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/prestamos")
@AllArgsConstructor
public class PrestamoController {

	PrestamoService prestamoService;
	SolicitudPrestamoService solicitudPrestamoService;
	PrestamistaService prestamistaService;
	GrupoPrestamistaService grupoService;
	PrestatarioService prestatarioService;
	CuotaPrestamoService cuotaService;

	@GetMapping({ "", "/", "listar" })
	private String listar(Model model, HttpSession session,
			@RequestParam(name = "estado", required = false, defaultValue = "-1") int estado,
			@RequestParam(name = "fechaDesde", required = false, defaultValue = "") String fechaDesde,
			@RequestParam(name = "fechaHasta", required = false, defaultValue = "") String fechaHasta) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		int rol = userLogged.getRol().getIdRol();
		List<PrestamoDto> listaPrestamo = new ArrayList<PrestamoDto>();
		String estadoPrestamo = "";
		if (estado > 0)
			estadoPrestamo = (estado == 1) ? Utils.PAGO_PENDIENTE : Utils.PAGO_PAGADO;
		final String estadoPrestamoFinal = estadoPrestamo;

		if (rol == Utils.ROL_ADMINISTRADOR)
			// listaPrestamo = prestamoService.listar();

			if (rol == Utils.ROL_JEFE_PRESTAMISTA) {
				Prestamista jefePrestamista = prestamistaService
						.getPrestamistaById(userLogged.getPersona().getIdPersona())
						.orElseThrow();

				List<Prestamo> prestamos = grupoService.listByJefe(jefePrestamista).stream()
						.map(GrupoPrestamista::getAsesorPrestamista)
						.flatMap(asesor -> asesor.getPrestatariosList().stream()
								.flatMap(prestatario -> prestatario.getListaSolicitudPrestamo().stream()
										.filter(solicitud -> Utils.PRESTAMO_APROBADO
												.equalsIgnoreCase(solicitud.getEstado()))
										.map(solicitud -> prestamoService.getBySolicitud(solicitud))
										.filter(Objects::nonNull)))
						.collect(Collectors.toList());
				// listaPrestamo = prestamos;
			}

		if (rol == Utils.ROL_PRESTAMISTA) {
			Prestamista asesorPrestamista = prestamistaService
					.getPrestamistaById(userLogged.getPersona().getIdPersona())
					.orElseThrow();
			List<Prestamo> prestamos = asesorPrestamista.getPrestatariosList().stream()
					.flatMap(prestatario -> prestatario.getListaSolicitudPrestamo().stream()
							.filter(solicitud -> Utils.PRESTAMO_APROBADO.equalsIgnoreCase(solicitud.getEstado()))
							.map(solicitud -> prestamoService.getBySolicitud(solicitud))
							.filter(Objects::nonNull))
					.collect(Collectors.toList());
			// listaPrestamo = prestamos;
		}

		if (rol == Utils.ROL_PRESTATARIO) {
			Prestatario prestatario = prestatarioService.getPrestatarioById(userLogged.getPersona().getIdPersona())
					.orElseThrow();
			List<Prestamo> prestamos = prestatario.getListaSolicitudPrestamo().stream()
					.filter(solicitud -> Utils.PRESTAMO_APROBADO.equalsIgnoreCase(solicitud.getEstado()))
					.map(solicitud -> prestamoService.getBySolicitud(solicitud))
					.filter(Objects::nonNull)
					.collect(Collectors.toList());

			// listaPrestamo = prestamos;

			if (!estadoPrestamoFinal.isEmpty())
				prestamos = prestamos.stream().filter(prestamo -> {
					String estadoUltimaCuota = prestamo.getListaCuotaPrestamo().get(prestamo.getCuotas() - 1)
							.getEstado();
					if (estadoUltimaCuota.equals(Utils.PAGO_PARCIAL))
						estadoUltimaCuota = Utils.PAGO_PENDIENTE;
					return (estadoUltimaCuota.equals(estadoPrestamoFinal));
				}).collect(Collectors.toList());

			if (!fechaDesde.isEmpty())
				prestamos = prestamos.stream().filter(prestamo -> {
					return prestamo.getFechaRegistro().toString().compareTo(fechaDesde) >= 0;
				}).collect(Collectors.toList());

			if (!fechaHasta.isEmpty())
				prestamos = prestamos.stream().filter(prestamo -> {
					return prestamo.getFechaRegistro().toString().compareTo(fechaHasta) <= 0;
				}).collect(Collectors.toList());

			listaPrestamo = prestamosToDto(prestamos);
		}

		if (listaPrestamo.size() > 0)
			listaPrestamo.sort(Comparator.comparingInt(PrestamoDto::getIdPrestamo).reversed());

		model.addAttribute("listaPrestamo", listaPrestamo);

		if (rol == Utils.ROL_PRESTATARIO)
			return "prestatarioRevisaPrestamo";

		return "listaPrestamo";
	}

	@GetMapping("detalle/{id}")
	@ResponseBody
	private HashMap<?, ?> detalle(@PathVariable int id, Model model) {

		Prestamo prestamo = prestamoService.buscarPorId(id);
		List<CuotaPrestamo> cuotas = cuotaService.listarPorId(id);

		double montosPagados = 0;
		double montosPendientes = 0;
		int cantCuotasPagadas = 0;
		int cantCuotasPendientes = 0;
		int nroCuotaActual = 0;

		for (CuotaPrestamo cuota : cuotas) {
			if (Utils.PAGO_PAGADO.equals(cuota.getEstado())) {
				montosPagados += cuota.getMontoTotal();
				cantCuotasPagadas++;
			} else {
				montosPendientes += cuota.getMontoTotal();
				cantCuotasPendientes++;
			}
		}

		if (cantCuotasPagadas == prestamo.getCuotas()) {
			nroCuotaActual = 0;
		} else {
			final int finalNroCuotaActual = cantCuotasPagadas + 1;
			nroCuotaActual = finalNroCuotaActual;
		}

		HashMap<String, Object> map = new HashMap<>();
		map.put("prestamo", prestamo);
		map.put("cuotas", cuotas);
		map.put("nroCuotaActual", nroCuotaActual);
		map.put("cantCuotasPagadas", cantCuotasPagadas);
		map.put("cantCuotasPendientes", cantCuotasPendientes);
		map.put("montosPagados", montosPagados);
		map.put("montosPendientes", montosPendientes);
		return map;
	}

	@GetMapping("/registrar/{id}")
	private String registrar(@PathVariable int id, Model model) {

		Prestamo prestamo = new Prestamo();
		SolicitudPrestamo solicitudPrestamo = new SolicitudPrestamo();
		// Prestamista prestamista = new Prestamista();

		solicitudPrestamo = solicitudPrestamoService.buscarPorId(id);
		// prestamista = prestamistaService.listarPrestamistaPorId(id);

		prestamo.setSolicitudPrestamo(solicitudPrestamo);

		model.addAttribute("prestamo", prestamo);

		return "guardarPrestamo";
	}

	private List<PrestamoDto> prestamosToDto(List<Prestamo> prestamos) {
		List<PrestamoDto> prestamosDto = new ArrayList<PrestamoDto>();

		for (Prestamo prestamo : prestamos) {
			PrestamoDto itemDto = new PrestamoDto();
			List<CuotaPrestamo> cuotas = cuotaService.listarPorId(prestamo.getIdPrestamo());

			double montosPagados = 0;
			double montosPendientes = 0;
			int cantCuotasPagadas = 0;
			int cantCuotasPendientes = 0;
			int nroCuotaActual = 0;

			for (CuotaPrestamo cuota : cuotas) {
				if (Utils.PAGO_PAGADO.equals(cuota.getEstado())) {
					montosPagados += cuota.getMontoTotal();
					cantCuotasPagadas++;
				} else {
					montosPendientes += cuota.getMontoTotal();
					cantCuotasPendientes++;
				}
			}

			if (cantCuotasPagadas == prestamo.getCuotas()) {
				nroCuotaActual = 0;
				itemDto.setEstadoPrestamo(Utils.PAGO_PAGADO);
				itemDto.setEstadoUltimaCuota(Utils.PAGO_PAGADO);
			} else {
				final int finalNroCuotaActual = cantCuotasPagadas + 1;
				itemDto.setEstadoPrestamo(Utils.PAGO_PENDIENTE);
				itemDto.setEstadoUltimaCuota(cuotas.stream()
						.filter(cuota -> cuota.getCuotaPrestamoPk().getIdCuotaPrestamo() == finalNroCuotaActual)
						.findFirst().orElseThrow().getEstado());
				nroCuotaActual = finalNroCuotaActual;
			}

			itemDto.setIdPrestamo(prestamo.getIdPrestamo());
			itemDto.setSolicitudPrestamo(prestamo.getSolicitudPrestamo());
			itemDto.setMonto(prestamo.getMonto());
			itemDto.setCuotas(prestamo.getCuotas());
			itemDto.setTea(prestamo.getTea());
			itemDto.setTem(prestamo.getTem());
			itemDto.setFechaRegistro(prestamo.getFechaRegistro());
			itemDto.setActivo(prestamo.isActivo());
			itemDto.setNroCuotaActual(nroCuotaActual);
			itemDto.setCantCuotasPagadas(cantCuotasPagadas);
			itemDto.setCantCuotasPendientes(cantCuotasPendientes);
			itemDto.setMontosPagados(montosPagados);
			itemDto.setMontosPendientes(montosPendientes);
			itemDto.setListaCuotaPrestamo(cuotas);

			prestamosDto.add(itemDto);
		}
		return prestamosDto;
	}

}
