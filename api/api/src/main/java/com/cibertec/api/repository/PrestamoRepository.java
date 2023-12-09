package com.cibertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.CuotaPrestamoPK;
import com.cibertec.api.model.Prestamo;
import com.cibertec.api.model.SolicitudPrestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    Prestamo findBySolicitudPrestamo(SolicitudPrestamo solicitudPrestamo);
}
