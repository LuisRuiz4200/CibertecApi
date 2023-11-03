package com.cibertec.api.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.api.model.Banco;

public interface BancoService {
    public abstract List<Banco> getAll();
    public abstract Optional<Banco> getById(int id);
}
