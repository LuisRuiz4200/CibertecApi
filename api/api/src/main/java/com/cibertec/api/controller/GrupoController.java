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
import com.cibertec.api.model.GrupoPrestamistaId;
import com.cibertec.api.model.PersonaM;
import com.cibertec.api.model.PrestamistaM;
import com.cibertec.api.model.modelDto.GrupoPersonaDto;
import com.cibertec.api.service.GrupoPrestamistaService;
import com.cibertec.api.service.GrupoService;
import com.cibertec.api.service.PrestamistaMService;
import com.cibertec.api.service.personaService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(value = "grupo")
@AllArgsConstructor
public class GrupoController {
    private GrupoService grupoService;
    private GrupoPrestamistaService grupoPrestamistaService;
    private PrestamistaMService prestamistaMService;
    private personaService personaService;
    
    @GetMapping({"/listar", "", "/"})
    public String listGrupo(Model model){
        PrestamistaM prestamistaM = prestamistaMService.getPrestamistaById(2).orElse(null);

        List<Grupo> listGrupo = (prestamistaM != null) 
            ? prestamistaM.getGrupos() 
            : new ArrayList<>();

        model.addAttribute("list", listGrupo);
        return "GrupoListar";
    }

    @GetMapping("form/{id}")
    public String addGrupo(@PathVariable(name="id") int id, Model model){
        Grupo grupo = (id == 0) ? new Grupo() : grupoService.getGrupoById(id).orElse(null);

        String formType = (id == 0) ? "post" : "put";
        String title = (id == 0) ? "Registrar Grupo" : "Actualizar Grupo";
        
        model.addAttribute("formType", formType);
        model.addAttribute("title", title);
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
        if(prestamistaM != null){
            List<Grupo> grupos = prestamistaM.getGrupos();
            grupos.add(newGrupo);
            prestamistaM.setGrupos(grupos);
            prestamistaMService.guardarPrestamista(prestamistaM);

            // Registrar campo activo de GrupoPrestamista
            GrupoPrestamistaId grupoPrestamistaId = new GrupoPrestamistaId(newGrupo.getIdGrupo(), prestamistaM.getIdPrestamista());

            GrupoPrestamista newGrupoPrestamista = new GrupoPrestamista();
            newGrupoPrestamista.setId(grupoPrestamistaId);
            newGrupoPrestamista.setActivo(true);
            
            grupoPrestamistaService.saveGrupoPrestamista(newGrupoPrestamista);
        }

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

    @GetMapping("eliminar/{id}")
    public String deleteGrupo(@PathVariable(name="id") int id, Model model){
        if(id <= 0)
            return "GrupoListar";

        Grupo grupo = grupoService.getGrupoById(id).orElse(null);
        if(grupo == null)
            return "GrupoListar";
        
        PrestamistaM prestamistaM = new PrestamistaM();
        prestamistaM.setIdPrestamista(2);

        GrupoPrestamista grupoPrestamista = grupoPrestamistaService.getGrupoPrestamistaByGrupoAndPrestamista(id, prestamistaM.getIdPrestamista());

        if(grupoPrestamista == null)
            return "GrupoListar";

        grupoPrestamista.setActivo(false);
        grupoPrestamistaService.saveGrupoPrestamista(grupoPrestamista);
        return "redirect:/grupo";
    }
    
    @GetMapping("{id}/newMember")
    public String newMember(@PathVariable(name="id") int id, Model model){
        if(id <= 0)
            return "GrupoListar";
        Grupo grupo = grupoService.getGrupoById(id).orElse(null);
        if(grupo == null)
            return "GrupoListar";

        GrupoPersonaDto grupoPersonaDto = new GrupoPersonaDto();
        grupoPersonaDto.setIdGrupo(grupo.getIdGrupo());
        grupoPersonaDto.setDescripcion(grupo.getDescripcion());

        List<PersonaM> personaList = personaService.listarPersona();

        model.addAttribute("formType", "new");
        model.addAttribute("title", "Agregar miembro");
        model.addAttribute("grupoPersonaDto", grupoPersonaDto);
        model.addAttribute("cboPersonas", personaList);

        return "GrupoForm";
    }

    @PostMapping("newMember")
    public String newMember(@ModelAttribute("grupoPersonaDto") GrupoPersonaDto dtoModel, BindingResult result, Model model, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("error", dtoModel);
            return "GrupoForm";
        }

        PrestamistaM prestamistaM = prestamistaMService.getPrestamistaById(dtoModel.getIdPersona()).orElse(null);
        Grupo newGrupo = grupoService.getGrupoById(dtoModel.getIdGrupo()).orElse(null);

        if(prestamistaM == null || newGrupo == null)
            return "GrupoForm";
        
        List<Grupo> listGrupo = (prestamistaM.getGrupos() != null) 
            ? prestamistaM.getGrupos() 
            : new ArrayList<>();

        
        listGrupo.add(newGrupo);

        prestamistaM.setGrupos(listGrupo);
        prestamistaMService.guardarPrestamista(prestamistaM);
        status.setComplete();
        return "redirect:/grupo";
    }
}
