package com.cibertec.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.cibertec.api.model.Grupo;
import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.PrestamistaM;
import com.cibertec.api.service.GrupoPrestamistaService;
import com.cibertec.api.service.GrupoService;
import com.cibertec.api.service.PrestamistaMService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(value = "grupo")
@AllArgsConstructor
public class GrupoController {
    private GrupoService grupoService;
    private GrupoPrestamistaService grupoPrestamistaService;
    private PrestamistaMService prestamistaMService;
    
    @GetMapping({"/listar", "", "/"})
    public String listGrupo(Model model){
        PrestamistaM prestamistaM = prestamistaMService.getPrestamistaById(2).orElse(null);
        List<Grupo> listGrupo = new ArrayList<Grupo>();
        if(prestamistaM != null){
            List<Grupo> listItems = prestamistaM.getGrupos();
            for (Grupo grupo : listItems) {
                listGrupo.add(grupo);
            }
        }

        model.addAttribute("list", listGrupo);
        return "GrupoListar";
    }

    @GetMapping("form/{id}")
    public String addGrupo(@PathVariable(name="id") int id, Model model){
        Grupo grupo = null;
        if(id == 0){
            grupo = new Grupo();
            model.addAttribute("formType", "post");
            model.addAttribute("title", "Registrar Grupo");
        }else{
            grupo = grupoService.getGrupoById(id).orElse(null);
            model.addAttribute("formType", "put");
            model.addAttribute("title", "Actualizar Grupo");
        }
        model.addAttribute("grupo", grupo);
        return "GrupoForm";
    }

    @PostMapping("registrar")
    public String addGrupo(@ModelAttribute("grupo") Grupo grupo, BindingResult result, Model model, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("error", grupo);
            return "GrupoForm";
        }
        // Guardar nuevo grupo.
        Grupo newGrupo = grupoService.saveGrupo(grupo);
        // Buscar al prestamista en sesion, obtener todos sus grupos y asignar el nuevo grupo. Por ultimo guardar los cambios.
        PrestamistaM prestamistaM = prestamistaMService.getPrestamistaById(2).orElse(null);
        List<Grupo> grupos = prestamistaM.getGrupos();
        grupos.add(newGrupo);
        prestamistaM.setGrupos(grupos);
        prestamistaMService.guardarPrestamista(prestamistaM);

        status.setComplete();
        return "redirect:/grupo";
    }


    @PostMapping("actualizar")
    public String editGrupo(@ModelAttribute("grupo") Grupo grupo, BindingResult result, Model model, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("error", grupo);
            return "GrupoForm";
        }

        grupoService.saveGrupo(grupo);
        status.setComplete();

        return "redirect:/grupo";
    }

}
