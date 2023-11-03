package com.cibertec.api.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Table(name = "tb_cuenta")
@NoArgsConstructor
@Getter
@Setter
public class Cuenta {
    @Id
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
