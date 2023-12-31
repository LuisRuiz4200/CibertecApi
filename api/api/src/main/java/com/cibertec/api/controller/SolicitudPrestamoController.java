package com.cibertec.api.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.api.model.Banco;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.model.SolicitudPrestamo;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.BancoService;
import com.cibertec.api.service.PrestamistaService;
import com.cibertec.api.service.PrestatarioService;
import com.cibertec.api.service.SolicitudPrestamoService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/web/solicitudPrestamo")
@AllArgsConstructor
public class SolicitudPrestamoController {

	SolicitudPrestamoService solicitudPrestamoService;
	PrestamistaService prestamistaService;
	PrestatarioService prestatarioService;
	BancoService bancoService;

	@GetMapping("/listar")
	private String listar(Model model, HttpSession session) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		List<SolicitudPrestamo> listaSolicitudPrestamo = new ArrayList<SolicitudPrestamo>();
		listaSolicitudPrestamo = solicitudPrestamoService.listar();
		List<Prestatario> listaPrestatario = prestatarioService.listarPrestatario();
		Prestatario prestatario = new Prestatario();

		model.addAttribute("listaSolicitudPrestamo", listaSolicitudPrestamo);
		model.addAttribute("listaPrestatario", listaPrestatario);
		model.addAttribute("prestatario", prestatario);

		return "listaSolicitudPrestamo";
	}

	@GetMapping("/listar/")
	private String listarPorPrestamista(@RequestParam("idPrestatario") int idPrestatario, Model model,
			HttpSession session) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		List<SolicitudPrestamo> listaSolicitudPrestamo = new ArrayList<SolicitudPrestamo>();
		listaSolicitudPrestamo = solicitudPrestamoService.listarPorPrestatario(idPrestatario);
		List<Prestatario> listaPrestatario = prestatarioService.listarPrestatario();
		Prestatario prestatario = prestatarioService.listarPrestatarioPorId(idPrestatario);

		model.addAttribute("prestatario", prestatario);
		model.addAttribute("listaSolicitudPrestamo", listaSolicitudPrestamo);
		model.addAttribute("listaPrestatario", listaPrestatario);

		return "listaSolicitudPrestamo";
	}

	@GetMapping("/registrar")
	private String registrar(Model model, HttpSession session) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		SolicitudPrestamo solicitudPrestamo = new SolicitudPrestamo();
		solicitudPrestamo.setEstado("PENDIENTE DE ENVIO");
		solicitudPrestamo.setFechaRegistro(new Date(new java.util.Date().getTime()));

		List<Prestatario> listaPrestatario = prestatarioService.listarPrestatario();

		List<Banco> bancosList = bancoService.getAll();
		System.out.println(bancosList);
		model.addAttribute("bancos", bancosList);

		model.addAttribute("solicitudPrestamo", solicitudPrestamo);
		model.addAttribute("listaPrestatario", listaPrestatario);

		return "guardarSolicitudPrestamo";
	}

	@GetMapping("/actualizar/{id}")
	private String actualizar(@PathVariable int id, Model model, HttpSession session) {

		Usuario userLogged = (Usuario) session.getAttribute("UserLogged");
		if (userLogged == null)
			return "redirect:/login";

		SolicitudPrestamo solicitudPrestamo = new SolicitudPrestamo();
		solicitudPrestamo = solicitudPrestamoService.buscarPorId(id);

		List<Prestatario> listaPrestatario = prestatarioService.listarPrestatario();

		model.addAttribute("solicitudPrestamo", solicitudPrestamo);
		model.addAttribute("listaPrestatario", listaPrestatario);

		return "guardarSolicitudPrestamo";
	}

	@PostMapping("/guardar")
	private String registrar(@ModelAttribute SolicitudPrestamo solicitudPrestamo) {

		solicitudPrestamoService.guardar(solicitudPrestamo);

		return "redirect:/solicitudPrestamo/registrar";
	}

}
