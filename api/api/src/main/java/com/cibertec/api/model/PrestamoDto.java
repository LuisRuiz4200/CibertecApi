package com.cibertec.api.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
public class PrestamoDto {
    private int idPrestamo;
    private SolicitudPrestamo solicitudPrestamo;
    private double monto;
    private int cuotas;
    private double tea;
    private double tem;
    private Date fechaRegistro;
    private boolean activo;

    private String estadoPrestamo;
    private String estadoUltimaCuota;

    private int nroCuotaActual;
    private int cantCuotasPagadas;
    private int cantCuotasPendientes;

    private double montosPagados;
    private double montosPendientes;

    @ToString.Exclude
    @JsonIgnore
    private List<CuotaPrestamo> listaCuotaPrestamo;
}
