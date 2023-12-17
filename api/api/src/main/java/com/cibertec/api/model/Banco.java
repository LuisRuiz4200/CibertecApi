package com.cibertec.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_banco")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Banco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBanco;
    private String descripcion;

    @OneToMany(mappedBy = "idBancoCuenta")
    @ToString.Exclude
    @JsonIgnore
    private List<Cuenta> cuentaList;
}
