package com.cibertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.GrupoPrestamista;

import java.util.List;


@Repository
public interface GrupoPrestamistaRepository extends JpaRepository<GrupoPrestamista, Integer>{
    List<GrupoPrestamista> findByIdJefePrestamista(int idJefePrestamista);
    List<GrupoPrestamista> findByIdJefePrestamistaAndActivo(int idJefePrestamista, boolean activo);

}

