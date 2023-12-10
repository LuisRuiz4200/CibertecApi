package com.cibertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.Prestamista;

import java.util.List;

@Repository
public interface GrupoPrestamistaRepository extends JpaRepository<GrupoPrestamista, Integer> {
    List<GrupoPrestamista> findByJefePrestamista(Prestamista jefePrestamista);

    List<GrupoPrestamista> findByJefePrestamistaAndActivo(Prestamista jefePrestamista, boolean activo);

    GrupoPrestamista findByJefePrestamistaAndAsesorPrestamista(Prestamista jefePrestamista,
            Prestamista asesorPrestamista);
}
