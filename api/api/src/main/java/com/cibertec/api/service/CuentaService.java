package com.cibertec.api.service;

import java.util.List;
import java.util.Optional;

import com.cibertec.api.model.Banco;
import com.cibertec.api.model.Cuenta;
import com.cibertec.api.model.Prestatario;

public interface CuentaService {
    public abstract List<Cuenta> getAllByPrestatario(Prestatario prestatario);
    public abstract List<Cuenta> getAllByBanco(Banco banco);
    public abstract Optional<Cuenta> getById(int id);
    public abstract Cuenta addOrUpdate(Cuenta cuenta);
    public abstract void delete(Cuenta cuenta); 
}
