package com.cibertec.api.service;

import java.util.List;

import com.cibertec.api.model.GrupoPrestamista;

public interface GrupoPrestamistaService {
    List<GrupoPrestamista> getGrupoPrestamistaList();
    List<GrupoPrestamista> getGrupoPrestamistaByPrestamista(int idPrestamista);
    GrupoPrestamista saveGrupoPrestamista(GrupoPrestamista grupoPrestamista);
    GrupoPrestamista getGrupoPrestamistaByGrupoAndPrestamista(int grupoId, int prestamistaId);
}
