package com.cibertec.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.SolicitudPrestamo;

@Repository
public interface SolicitudPrestamisteRepository extends JpaRepository<SolicitudPrestamo, Integer> {

	public List<SolicitudPrestamo> findByPrestatarioIdPrestatario(int idPrestatario);
}
