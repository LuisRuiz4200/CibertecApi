package com.cibertec.api.controller;

import java.util.Date;
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

		if(UserLogged == null)
			return "redirect:/login";

	    int idPrestamista = UserLogged.getPersona().getIdPersona();
		Prestamista jefePrestamista = prestamistaService.getPrestamistaById(idPrestamista).orElse(null);

		if(jefePrestamista == null)
			return "intranet";
		Prestamista newPrestamista = new Prestamista();
		newPrestamista.setIdPrestamista(16);
		print(insertGrupoPrestamista(jefePrestamista, newPrestamista, UserLogged));
		return "intranet";
	}

	public List<GrupoPrestamista> listGrupoByJefePrestamista(Prestamista jefePrestamista){
		List<GrupoPrestamista> list = grupoPrestamistaService.listByJefe(jefePrestamista);
		return list;
	}

	public List<GrupoPrestamista> listGrupoByJefePrestamistaAndActivo(Prestamista jefePrestamista){
		List<GrupoPrestamista> list = grupoPrestamistaService.listByJefeAndActivo(jefePrestamista, true);
		return list;
	}

	public GrupoPrestamista insertGrupoPrestamista(Prestamista jefePrestamista, Prestamista newPrestamista, Usuario usuario){
		GrupoPrestamista grupo = new GrupoPrestamista();
		grupo.setJefePrestamista(jefePrestamista);
		grupo.setAsesorPrestamista(newPrestamista);
		grupo.setActivo(true);
		grupo.setFechaRegistro(new Date());
		grupo.setFechaActualizacion(new Date());
		grupo.setUsuarioRegistro(usuario);
		grupo.setUsuarioActualiza(usuario);
		GrupoPrestamista response = grupoPrestamistaService.addOrUpdate(grupo);
		return response;
	}

	private void print(Object object){
		System.out.println("\n\n======================");
		System.out.println(object);
		System.out.println("======================\n\n");
	}
}
