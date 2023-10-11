package com.cibertec.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.GrupoPrestamista;

@Repository
public interface GrupoPrestamistaRepository extends JpaRepository<GrupoPrestamista, Integer>{
    // @Query("select gp from GrupoPrestamista gp where idPrestamista = ?1")
    // public List<GrupoPrestamista> listByIdPrestamista(int idPrestamista);   
    
    // public List<GrupoPrestamista> findByPrestamistaIdPrestamista(int idPrestamista);

}

