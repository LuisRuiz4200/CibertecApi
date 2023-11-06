package com.cibertec.api.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitudDto {
    private int id;
    private int state;
    private boolean newBank;
    private Prestatario prestatario;
    private Prestamista prestamista;
    private Cuenta cuentaSolicitud;
    private double monto;
	private double interes;
	private int cuotas;
	private String motivo;
	private String cuentaBancaria;
    private Date fechaRegistro;
    private String estado;
	private boolean activo;
}
