package com.cibertec.api.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.repository.GrupoPrestamistaRepository;
import com.cibertec.api.service.GrupoPrestamistaService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GrupoPrestamistaServiceImpl implements GrupoPrestamistaService{
    @Autowired
    private GrupoPrestamistaRepository repository;

    @Override
    public List<GrupoPrestamista> getGrupoPrestamistaList() {
        return repository.findAll();
    }

    @Override
    public List<GrupoPrestamista> getGrupoPrestamistaByPrestamista(int idPrestamista) {
        return new ArrayList<GrupoPrestamista>();
    }

    @Override
    public GrupoPrestamista saveGrupoPrestamista(GrupoPrestamista grupoPrestamista) {
        return repository.save(grupoPrestamista);
    }

    @Override
    public GrupoPrestamista getGrupoPrestamistaByGrupoAndPrestamista(int grupoId, int prestamistaId) {
        return repository.findById_IdGrupoAndId_IdPrestamista(grupoId, prestamistaId);
    }

    @Override
    public List<GrupoPrestamista> getGrupoPrestamistaByGrupo(int idGrupo) {
        return repository.findById_IdGrupo(idGrupo);
    }

    @Override
    public List<GrupoPrestamista> getByGrupoAndState(int idGrupo, boolean activo) {
        return repository.findById_IdGrupoAndActivo(idGrupo, activo);
    }
    
}
