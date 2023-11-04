package com.cibertec.api.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_cuenta")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cuenta {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int idCuenta;

    @ManyToOne
    @JoinColumn(name = "idBanco")
    private Banco idBancoCuenta;

    @ManyToOne
    @JoinColumn(name = "idPrestatario")
    private Prestatario idPrestamistaCuenta;

    private String numero;
    private boolean activo;
    private Date fechaRegistro;
    private Date fechaEdicion;
    private int usuarioRegistro;
    private int usuarioEdicion;
}
