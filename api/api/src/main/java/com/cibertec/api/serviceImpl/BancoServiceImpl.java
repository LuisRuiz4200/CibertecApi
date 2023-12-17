package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Banco;
import com.cibertec.api.repository.BancoRepository;
import com.cibertec.api.service.BancoService;

@Service
public class BancoServiceImpl implements BancoService {
    @Autowired
    private BancoRepository repository;

    @Override
    public List<Banco> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Banco> getById(int id) {
        return repository.findById(id);
    }
}
