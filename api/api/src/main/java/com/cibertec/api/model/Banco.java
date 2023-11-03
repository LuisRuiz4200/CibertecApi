package com.cibertec.api.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_banco")
@Data
public class Banco {
    @Id
    private int idBanco;
    private String nombre;

    @OneToMany(mappedBy = "idBancoCuenta")
    private List<Cuenta> cuentaList;
}
