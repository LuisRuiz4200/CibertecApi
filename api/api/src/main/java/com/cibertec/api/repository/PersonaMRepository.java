package com.cibertec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.Persona;

@Repository
public interface PersonaMRepository extends JpaRepository<Persona,Integer> {

	
	
}
