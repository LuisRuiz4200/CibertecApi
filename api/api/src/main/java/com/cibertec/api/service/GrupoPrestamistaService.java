package com.cibertec.api.service;

import java.util.List;

import com.cibertec.api.model.GrupoPrestamista;

public interface GrupoPrestamistaService {
    List<GrupoPrestamista> listGrupoPrestamistaByJefe(int idJefePrestamista);
    GrupoPrestamista addOrUpdateGrupoPrestamista(GrupoPrestamista grupoPrestamista);
    GrupoPrestamista deleteGrupoPrestamista(GrupoPrestamista grupoPrestamista);
}
