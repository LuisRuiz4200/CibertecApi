package com.cibertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.Comprobante;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer>{

	
}
