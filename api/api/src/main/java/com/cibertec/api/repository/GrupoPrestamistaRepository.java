package com.cibertec.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.GrupoPrestamista;
import com.cibertec.api.model.GrupoPrestamistaId;

@Repository
public interface GrupoPrestamistaRepository extends JpaRepository<GrupoPrestamista, GrupoPrestamistaId>{
    // @Query("select gp from GrupoPrestamista gp where idPrestamista = ?1")
    // public List<GrupoPrestamista> listByIdPrestamista(int idPrestamista);   
    
    // public List<GrupoPrestamista> findByPrestamistaIdPrestamista(int idPrestamista);

    // GrupoPrestamista findByGrupoIdGrupoAndPrestamistaIdPrestamista(Long grupoId, Long prestamistaId);
    GrupoPrestamista findById_IdGrupoAndId_IdPrestamista(@Param("idGrupo") int idGrupo,  @Param("idPrestamista")int idPrestamista);
}

