package com.cibertec.api.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Prestamo;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.reuzable.Utils;
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

	@GetMapping({ "", "/", "listar" })
	private String listar(Model model, HttpSession session) {
		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		int rol = userLogged.getRol().getIdRol();
		List<Prestamo> listaPrestamo = new ArrayList<Prestamo>();

		if (rol == Utils.ROL_ADMINISTRADOR)
			listaPrestamo = prestamoService.listar();

		if (rol == Utils.ROL_JEFE_PRESTAMISTA) {
			Prestamista jefePrestamista = prestamistaService.getPrestamistaById(userLogged.getPersona().getIdPersona())
					.orElseThrow();

			// List<GrupoPrestamista> grupoAsignado =
			// grupoService.listByJefe(jefePrestamista);
			// print(grupoAsignado);

			// List<Prestamista> asesores = grupoAsignado.stream().map(item ->
			// item.getAsesorPrestamista())
			// .collect(Collectors.toList());
			// print(asesores);

			// List<Prestatario> pretatarios = asesores.stream().flatMap(asesor ->
			// asesor.getPrestatariosList().stream())
			// .collect(Collectors.toList());
			// print(pretatarios);

			// List<SolicitudPrestamo> solicitudes = pretatarios.stream()
			// .flatMap(prestatario -> prestatario.getListaSolicitudPrestamo().stream()
			// .filter(solicitud -> "Aprobado".equals(solicitud.getEstado())))
			// .collect(Collectors.toList());
			// print(solicitudes);

			// List<Prestamo> prestamos = solicitudes.stream().map(solicitud ->
			// prestamoService.getBySolicitud(solicitud))
			// .filter(Objects::nonNull)
			// .collect(Collectors.toList());

			List<Prestamo> prestamos = grupoService.listByJefe(jefePrestamista).stream()
					.map(GrupoPrestamista::getAsesorPrestamista)
					.flatMap(asesor -> asesor.getPrestatariosList().stream()
							.flatMap(prestatario -> prestatario.getListaSolicitudPrestamo().stream()
									.filter(solicitud -> "Aprobado".equalsIgnoreCase(solicitud.getEstado()))
									.map(solicitud -> prestamoService.getBySolicitud(solicitud))
									.filter(Objects::nonNull)))
					.collect(Collectors.toList());
			listaPrestamo = prestamos;
		}

		if (rol == Utils.ROL_PRESTAMISTA) {
			Prestamista asesorPrestamista = prestamistaService
					.getPrestamistaById(userLogged.getPersona().getIdPersona())
					.orElseThrow();
			List<Prestamo> prestamos = asesorPrestamista.getPrestatariosList().stream()
					.flatMap(prestatario -> prestatario.getListaSolicitudPrestamo().stream()
							.filter(solicitud -> "Aprobado".equalsIgnoreCase(solicitud.getEstado()))
							.map(solicitud -> prestamoService.getBySolicitud(solicitud))
							.filter(Objects::nonNull))
					.collect(Collectors.toList());
			listaPrestamo = prestamos;
		}

		if (rol == Utils.ROL_PRESTATARIO) {
			Prestatario prestatario = prestatarioService.getPrestatarioById(userLogged.getPersona().getIdPersona())
					.orElseThrow();
			List<Prestamo> prestamos = prestatario.getListaSolicitudPrestamo().stream()
					.filter(solicitud -> "Aprobado".equalsIgnoreCase(solicitud.getEstado()))
					.map(solicitud -> prestamoService.getBySolicitud(solicitud))
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			listaPrestamo = prestamos;
		}

		if (listaPrestamo.size() > 0)
			listaPrestamo.sort(Comparator.comparingInt(Prestamo::getIdPrestamo).reversed());

		model.addAttribute("listaPrestamo", listaPrestamo);

		if (rol == Utils.ROL_PRESTATARIO)
			return "prestatarioRevisaPrestamo";

		return "listaPrestamo";
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

}
