package com.cibertec.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.Prestamista;
import com.cibertec.api.repository.GrupoPrestamistaRepository;
import com.cibertec.api.service.GrupoPrestamistaService;

@Service
public class GrupoPrestamistaServiceImpl implements GrupoPrestamistaService{

    @Autowired
    private GrupoPrestamistaRepository repository;

    @Override
    public List<GrupoPrestamista> listByJefe(Prestamista jefePrestamista) {
        return repository.findByJefePrestamista(jefePrestamista);
    }

    @Override
    public List<GrupoPrestamista> listByJefeAndActivo(Prestamista jefePrestamista, boolean activo) {
        return repository.findByJefePrestamistaAndActivo(jefePrestamista, activo);
    }

    @Override
    public GrupoPrestamista addOrUpdate(GrupoPrestamista grupoPrestamista) {
        return repository.save(grupoPrestamista);
    }

    @Override
    public GrupoPrestamista delete(GrupoPrestamista grupoPrestamista) {
        grupoPrestamista.setActivo(false);
        return repository.save(grupoPrestamista);
    }

    @Override
    public GrupoPrestamista getByJefeAndAsesor(Prestamista jefePrestamista, Prestamista asesorPrestamista) {
        return repository.findByJefePrestamistaAndAsesorPrestamista(jefePrestamista, asesorPrestamista);
    }
    
}
