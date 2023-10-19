package com.cibertec.api.service;

import java.util.List;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.Prestamista;

public interface GrupoPrestamistaService {
    List<GrupoPrestamista> listByJefe(Prestamista jefePrestamista);
    List<GrupoPrestamista> listByJefeAndActivo(Prestamista jefePrestamista, boolean activo);
    GrupoPrestamista addOrUpdate(GrupoPrestamista grupoPrestamista);
    GrupoPrestamista delete(GrupoPrestamista grupoPrestamista);
}
