package com.cibertec.api.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.service.GrupoPrestamistaService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
// @RequestMapping("grupo")
@AllArgsConstructor
public class GrupoPrestamistaController {
	private GrupoPrestamistaService grupoPrestamistaService;

	// @GetMapping({"/listar", "", "/"})
	public String listGrupo(Model model, HttpSession session){
		return "intranet";
	}

	public List<Prestamista> listGrupoByJefePrestamista(Prestamista jefePrestamista){
		List<GrupoPrestamista> list = grupoPrestamistaService.listByJefe(jefePrestamista);

		List<Prestamista> listPrestamistas = list.stream().map(GrupoPrestamista::getAsesorPrestamista).collect(Collectors.toList());
		return listPrestamistas;
	}

	public List<Prestamista> listGrupoByJefePrestamistaAndActivo(Prestamista jefePrestamista){
		List<GrupoPrestamista> list = grupoPrestamistaService.listByJefeAndActivo(jefePrestamista, true);

		List<Prestamista> listPrestamistas = list.stream().map(GrupoPrestamista::getAsesorPrestamista).collect(Collectors.toList());
		return listPrestamistas;
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

	public GrupoPrestamista deleteGrupoPrestamista(Prestamista jefePrestamista, Prestamista asesorPrestamista, Usuario usuario){
		GrupoPrestamista grupo = new GrupoPrestamista();
		grupo.setJefePrestamista(jefePrestamista);
		grupo.setAsesorPrestamista(asesorPrestamista);
		return grupoPrestamistaService.delete(grupo);
	}

	private void print(Object object){
		System.out.println("\n\n======================");
		System.out.println(object);
		System.out.println("======================\n\n");
	}
}
