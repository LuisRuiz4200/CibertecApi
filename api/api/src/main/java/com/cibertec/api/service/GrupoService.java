package com.cibertec.api.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.api.model.Grupo;

public interface GrupoService{
    public abstract Grupo saveGrupo(Grupo grupo);
    public abstract void deleteGrupo(Grupo grupo);
    public abstract List<Grupo> getGrupoList();
    public abstract Optional<Grupo> getGrupoById(int idGrupo);
}
