package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Grupo;
import com.cibertec.api.repository.GrupoRepository;
import com.cibertec.api.service.GrupoService;

@Service
public class GrupoServiceImpl implements GrupoService{
    @Autowired
    private GrupoRepository repository; 

    @Override
    public Grupo saveGrupo(Grupo grupo) {
        return repository.save(grupo);
    }

    @Override
    public void deleteGrupo(Grupo grupo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteGrupo'");
    }

    @Override
    public List<Grupo> getGrupoList() {
        return repository.findAll();
    }

    @Override
    public Optional<Grupo> getGrupoById(int idGrupo) {
        return repository.findById(idGrupo);
    }
    
}
