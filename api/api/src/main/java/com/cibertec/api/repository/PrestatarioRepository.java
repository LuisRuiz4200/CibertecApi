package com.cibertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.Prestatario;

@Repository
public interface PrestatarioRepository extends JpaRepository<Prestatario, Integer> {

}
