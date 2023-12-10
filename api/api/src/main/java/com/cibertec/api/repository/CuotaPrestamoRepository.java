package com.cibertec.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.CuotaPrestamo;
import com.cibertec.api.model.CuotaPrestamoPK;

@Repository
public interface CuotaPrestamoRepository extends JpaRepository<CuotaPrestamo, CuotaPrestamoPK> {

	public List<CuotaPrestamo> findByCuotaPrestamoPkIdPrestamo(int idPrestamo);

	public void deleteByCuotaPrestamoPk(CuotaPrestamoPK cuotaPrestamoPK);

}
