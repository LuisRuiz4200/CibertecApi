package com.cibertec.api.controllerrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.service.SolicitudPrestamoService;

@RestController
@RequestMapping("/api/solicitudPrestamo")
public class SolicitudPrestamoApiController {

	@Autowired
	SolicitudPrestamoService solicitudPrestamoService;

	@GetMapping("/buscar/{id}")
	@ResponseBody
	private ResponseEntity<SolicitudPrestamo> buscar(@PathVariable int id) {

		SolicitudPrestamo solicitudPrestamo = new SolicitudPrestamo();

		solicitudPrestamo = solicitudPrestamoService.buscarPorId(id);

		return ResponseEntity.ok(solicitudPrestamo);
	}

}
