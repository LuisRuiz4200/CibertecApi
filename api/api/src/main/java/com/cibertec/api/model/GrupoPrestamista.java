package com.cibertec.api.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_grupo_prestamista")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GrupoPrestamista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGrupoPrestamista;

    @ManyToOne
    @JoinColumn(name = "idJefePrestamista")
    private Prestamista jefePrestamista = new Prestamista();

    @ManyToOne
    @JoinColumn(name = "idAsesorPrestamista")
    private Prestamista asesorPrestamista = new Prestamista();

    private boolean activo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "usuarioRegistro", referencedColumnName = "idUsuario")
    private Usuario usuarioRegistro;

    @ManyToOne
    @JoinColumn(name = "usuarioActualiza", referencedColumnName = "idUsuario")
    private Usuario usuarioActualiza;

    public boolean getActivo() {
        return this.activo;
    }
}
