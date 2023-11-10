package com.cibertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer>{

}
