package com.cibertec.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.api.model.ComprobanteDetalle;
import com.cibertec.api.model.ComprobanteDetallePK;

@Repository
public interface ComprobanteDetalleRepository extends JpaRepository<ComprobanteDetalle, ComprobanteDetallePK> {

	public List<ComprobanteDetalle> findByComprobanteDetallePKIdComprobante(int idComprobante);

}
