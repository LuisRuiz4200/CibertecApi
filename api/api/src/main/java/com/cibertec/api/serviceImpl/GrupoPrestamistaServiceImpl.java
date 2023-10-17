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
    public List<GrupoPrestamista> listGrupoPrestamistaByJefe(int idJefePrestamista) {
        return repository.findByIdJefePrestamista(idJefePrestamista);
    }

    @Override
    public GrupoPrestamista addOrUpdateGrupoPrestamista(GrupoPrestamista grupoPrestamista) {
        return repository.save(grupoPrestamista);
    }

    @Override
    public GrupoPrestamista deleteGrupoPrestamista(GrupoPrestamista grupoPrestamista) {
        return repository.save(grupoPrestamista);
    }
    
}
