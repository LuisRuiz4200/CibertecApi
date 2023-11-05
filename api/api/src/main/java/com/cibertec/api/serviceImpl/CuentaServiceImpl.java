package com.cibertec.api.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.api.model.Banco;
import com.cibertec.api.model.Cuenta;
import com.cibertec.api.model.Prestatario;
import com.cibertec.api.repository.CuentaRepository;
import com.cibertec.api.service.CuentaService;

@Service
public class CuentaServiceImpl implements CuentaService{
    @Autowired
    private CuentaRepository repository;

    @Override
    public List<Cuenta> getAllByPrestatario(Prestatario prestatario) {
        return repository.findByIdPrestatarioCuenta(prestatario);
    }

    @Override
    public List<Cuenta> getAllByBanco(Banco banco) {
        return repository.findByIdBancoCuenta(banco);
    }

    @Override
    public Optional<Cuenta> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public Cuenta addOrUpdate(Cuenta cuenta) {
        return repository.save(cuenta);
    }

    @Override
    public void delete(Cuenta cuenta) {
        cuenta.setActivo(false);
        repository.save(cuenta);
    }
}
