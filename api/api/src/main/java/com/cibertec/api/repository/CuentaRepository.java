package com.cibertec.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.Banco;
import com.cibertec.api.model.Cuenta;
import com.cibertec.api.model.Prestatario;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{
    public abstract List<Cuenta> findByIdPrestatarioCuenta(Prestatario prestatario);
    public abstract List<Cuenta> findByIdBancoCuenta(Banco banco);
    public abstract Optional<Cuenta> findByIdBancoCuentaAndNumero(Banco banco, String numero);
}
