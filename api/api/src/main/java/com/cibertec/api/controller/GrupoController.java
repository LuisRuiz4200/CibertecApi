package com.cibertec.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cibertec.api.model.Grupo;
import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.PrestamistaM;
import com.cibertec.api.service.GrupoPrestamistaService;
import com.cibertec.api.service.GrupoService;
import com.cibertec.api.service.PrestamistaMService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class GrupoController {
    private GrupoService grupoService;
    private GrupoPrestamistaService grupoPrestamistaService;
    private PrestamistaMService prestamistaMService;
    
    @GetMapping("listarGrupo")
    public String listGrupo(Model model){
        PrestamistaM prestamistaM = prestamistaMService.getPrestamistaById(2).orElse(null);
        List<Grupo> listGrupo = new ArrayList<Grupo>();
        if(prestamistaM != null){
            List<Grupo> listItems = prestamistaM.getGrupos();
            for (Grupo grupo : listItems) {
                listGrupo.add(grupo);
            }
        }

        // List<GrupoPrestamista> xList = prestamistaMService.listarPrestamistaPorId(prestamistaM.getIdPrestamista()).getListPrestamista();

        // List<Grupo> listGrupo = new ArrayList<Grupo>();

        // for (GrupoPrestamista item : xList) {
        //     System.out.println(item.getIdGrupo());
        //     // grupoService.getGrupoById();
        // }

        // List<Grupo> listGrupo = grupoService.getGrupoList();
        model.addAttribute("list", listGrupo);
        return "listGrupo";
    }


}
