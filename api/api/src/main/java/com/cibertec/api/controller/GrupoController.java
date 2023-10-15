package com.cibertec.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.cibertec.api.model.Persona;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.model.Usuario;
import com.cibertec.api.model.modelDto.GrupoDto;
import com.cibertec.api.model.modelDto.GrupoPersonaDto;
import com.cibertec.api.service.GrupoPrestamistaService;
import com.cibertec.api.service.GrupoService;
import com.cibertec.api.service.PrestamistaService;

import jakarta.servlet.http.HttpSession;

import com.cibertec.api.service.PersonaService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(value = "grupo")
@AllArgsConstructor
public class GrupoController {
    private GrupoService grupoService;
    private GrupoPrestamistaService grupoPrestamistaService;
    private PrestamistaService prestamistaService;
    private PersonaService personaService;
    
    @GetMapping({"/listar", "", "/"})
    public String listGrupo(Model model, HttpSession session){
        Usuario userLogeado = (Usuario) session.getAttribute("UserLogin");
	    int idPrestamista = userLogeado.getPersona().getIdPersona();
	    
        Prestamista prestamista = prestamistaService.getPrestamistaById(idPrestamista).orElse(null);

        List<Grupo> listGrupo = (prestamista != null) 
            ? prestamista.getGrupos() 
            : new ArrayList<>();
        List<GrupoDto> grupoDtoList = new ArrayList<GrupoDto>();
        for (Grupo item : listGrupo) {
            int count = grupoPrestamistaService.getGrupoPrestamistaByGrupo(
            		item.getIdGrupo()).stream().filter(
            		prestamita -> prestamita.getActivo() == true).collect(Collectors.toList()).size();
            GrupoDto grupoDto = new GrupoDto(item.getIdGrupo(), item.getDescripcion(), (count - 1));
            grupoDtoList.add(grupoDto);
        }

        model.addAttribute("list", grupoDtoList);
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
    public String addGrupo(@ModelAttribute("grupo") Grupo grupo, BindingResult result, Model model, SessionStatus status, HttpSession session){
        Usuario userLogeado = (Usuario) session.getAttribute("UserLogin");
	    int idPrestamista = userLogeado.getPersona().getIdPersona();
	    
        if(result.hasErrors()){
            model.addAttribute("error", grupo);
            return "GrupoForm";
        }
        // Guardar nuevo grupo.
        Grupo newGrupo = grupoService.saveGrupo(grupo);
        // Buscar al prestamista en sesion, obtener todos sus grupos y asignar el nuevo grupo. Por ultimo guardar los cambios.
        Prestamista prestamista = prestamistaService.getPrestamistaById(idPrestamista).orElse(null);
        if(prestamista != null){
            List<Grupo> grupos = prestamista.getGrupos();
            grupos.add(newGrupo);
            prestamista.setGrupos(grupos);
            prestamistaService.guardarPrestamista(prestamista);

            // Registrar campo activo de GrupoPrestamista
            GrupoPrestamistaId grupoPrestamistaId = new GrupoPrestamistaId(newGrupo.getIdGrupo(), prestamista.getIdPrestamista());

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
    public String deleteGrupo(@PathVariable(name="id") int id, Model model, HttpSession session){
        if(id <= 0)
            return "redirect:/grupo";

        Grupo grupo = grupoService.getGrupoById(id).orElse(null);
        if(grupo == null)
            return "redirect:/grupo";
        
        Usuario userLogeado = (Usuario) session.getAttribute("UserLogin");
	    int idPrestamista = userLogeado.getPersona().getIdPersona();

        GrupoPrestamista grupoPrestamista = grupoPrestamistaService.getGrupoPrestamistaByGrupoAndPrestamista(id, idPrestamista);

        if(grupoPrestamista == null)
            return "redirect:/grupo";

        grupoPrestamista.setActivo(false);
        grupoPrestamistaService.saveGrupoPrestamista(grupoPrestamista);
        return "redirect:/grupo";
    }
    
    @GetMapping("{id}/newMember")
    public String newMember(@PathVariable(name="id") int id, Model model, HttpSession session){
        if(id <= 0)
            return "redirect:/grupo";
        Grupo grupo = grupoService.getGrupoById(id).orElse(null);
        if(grupo == null)
            return "redirect:/grupo";
        // Listado de personas para cboPersonas
        GrupoPersonaDto grupoPersonaDto = new GrupoPersonaDto();
        grupoPersonaDto.setIdGrupo(grupo.getIdGrupo());
        grupoPersonaDto.setDescripcion(grupo.getDescripcion());

        // Asignar el Id del jefe de la Sesion
        Usuario userLogeado = (Usuario) session.getAttribute("UserLogin");
	    int idPrestamista = userLogeado.getPersona().getIdPersona();

        // Obtener la lista de grupos con "estado" diferentes a 0
        List<GrupoPrestamista> gpActivos = grupoPrestamistaService.getByGrupoAndState(id, true);
        
        List<Persona> personaInThisGrupo = new ArrayList<Persona>();

        for (GrupoPrestamista item : gpActivos) {
            Persona persona = personaService.getById(item.getId().getIdPrestamista()).orElse(null);
            personaInThisGrupo.add(persona);
        }
        
        personaInThisGrupo = personaInThisGrupo.stream().filter(item -> item.getIdPersona() != idPrestamista).collect(Collectors.toList());
        
        // Quitar al Jefe Prestamista para la muestra en tabla
        List<GrupoPersonaDto> listForView = new ArrayList<GrupoPersonaDto>();

        for (Persona item : personaInThisGrupo) {
            GrupoPersonaDto dto = new GrupoPersonaDto();
                dto.setIdPersona(item.getIdPersona());
                dto.setNombres(item.getNombres());
                dto.setApellidos(item.getApellidos());
                dto.setEmail(item.getEmail());
                dto.setIdGrupo(grupo.getIdGrupo());
                dto.setDescripcion(grupo.getDescripcion());
                listForView.add(dto);
        }
       
        List<GrupoPrestamista> listPrestamistaWithGrupo = grupoPrestamistaService.getGrupoPrestamistaList();

        List<Persona> personaList = personaService.listarPersona();
        
        List<Persona> listaFiltrada = personaList.stream().filter(
          persona -> listPrestamistaWithGrupo.stream().noneMatch(
            personaWithGrupo -> personaWithGrupo.getId().getIdPrestamista() == persona.getIdPersona()
          )
        ).collect(Collectors.toList());

        model.addAttribute("formType", "new");
        model.addAttribute("title", "Agregar miembro");
        model.addAttribute("grupoPersonaDto", grupoPersonaDto);
        model.addAttribute("cboPersonas", listaFiltrada);
        model.addAttribute("tblPersonas", listForView);
        return "GrupoForm";
    }

    @PostMapping("newMember")
    public String newMember(@ModelAttribute("grupoPersonaDto") GrupoPersonaDto dtoModel, BindingResult result, Model model, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("error", dtoModel);
            return "GrupoForm";
        }

        Prestamista prestamista = prestamistaService.getPrestamistaById(dtoModel.getIdPersona()).orElse(null);
        Grupo newGrupo = grupoService.getGrupoById(dtoModel.getIdGrupo()).orElse(null);

        if(prestamista == null || newGrupo == null)
            return "GrupoForm";
        
        List<Grupo> listGrupo = (prestamista.getGrupos() != null) 
            ? prestamista.getGrupos() 
            : new ArrayList<>();

        
        listGrupo.add(newGrupo);

        prestamista.setGrupos(listGrupo);
        prestamistaService.guardarPrestamista(prestamista);

        // Registrar campo activo de GrupoPrestamista
        GrupoPrestamistaId grupoPrestamistaId = new GrupoPrestamistaId(dtoModel.getIdGrupo(), dtoModel.getIdPersona());

        GrupoPrestamista updateGrupoPrestamista = new GrupoPrestamista();
        updateGrupoPrestamista.setId(grupoPrestamistaId);
        updateGrupoPrestamista.setActivo(true);
        grupoPrestamistaService.saveGrupoPrestamista(updateGrupoPrestamista);

        status.setComplete();
        return "redirect:/grupo/" + dtoModel.getIdGrupo() + "/newMember";
    }

    @GetMapping("{grupo}/deleteMember/{id}")
    public String deleteMember(@PathVariable(name="id") int id, @PathVariable(name = "grupo")int idGrupo, Model model){
        Prestamista prestamista = prestamistaService.getPrestamistaById(id).orElse(null);
        Grupo thisGrupo = grupoService.getGrupoById(idGrupo).orElse(null);

        /* Eliminación fisica 
        List<Grupo> newGrupoList = prestamista.getGrupos().stream().filter(item -> item != thisGrupo).collect(Collectors.toList());
        prestamista.setGrupos(newGrupoList);
        prestamistaMService.guardarPrestamista(prestamista);*/

        /* Eliminacion logica */
        GrupoPrestamista grupoPrestamista = grupoPrestamistaService.getGrupoPrestamistaByGrupoAndPrestamista(thisGrupo.getIdGrupo(), prestamista.getIdPrestamista());
        grupoPrestamista.setActivo(false);
        grupoPrestamistaService.saveGrupoPrestamista(grupoPrestamista);
        return "redirect:/grupo/" + thisGrupo.getIdGrupo() + "/newMember";
    }
}
