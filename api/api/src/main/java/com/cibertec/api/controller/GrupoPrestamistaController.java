package com.cibertec.api.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.GrupoPrestamistaService;
import com.cibertec.api.service.PrestamistaService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("grupo")
@AllArgsConstructor
public class GrupoPrestamistaController {
	private GrupoPrestamistaService grupoPrestamistaService;
	private PrestamistaService prestamistaService;

	@GetMapping({"/listar", "", "/"})
	public String listGrupo(Model model, HttpSession session){
		Usuario UserLogged = (Usuario) session.getAttribute("UserLogged");
		System.out.println("\n\n======================");
		System.out.println(UserLogged);
		System.out.println("======================\n\n");
		if(UserLogged == null)
			return "redirect:/login";
	    int idPrestamista = UserLogged.getPersona().getIdPersona();
		Prestamista jefePrestamista = prestamistaService.getPrestamistaById(idPrestamista).orElse(null);

		if(jefePrestamista == null)
			return "intranet";
		List<GrupoPrestamista> list = grupoPrestamistaService.listByJefe(jefePrestamista);
		return "intranet";
	}
}
