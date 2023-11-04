package com.cibertec.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.Banco;
import com.cibertec.api.model.Cuenta;
import com.cibertec.api.model.Prestatario;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{
    public abstract List<Cuenta> findByIdPrestamistaCuenta(Prestatario prestatario);
    public abstract List<Cuenta> findByIdBancoCuenta(Banco banco);
}
