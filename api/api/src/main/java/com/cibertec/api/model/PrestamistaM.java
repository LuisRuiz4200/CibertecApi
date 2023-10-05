package com.cibertec.api.model;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tb_prestamista")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PrestamistaM {
	@Id
    @Column(name = "idprestamista")
    private int idPrestamista;
	//entidad PrestamistaM 1 by 1 PersonaM
	//@OneToOne(cascade = CascadeType.ALL) //Engloba a todos
	@OneToOne(cascade = {CascadeType.ALL})
	@MapsId //indica que el atributo idPrestamista de la entidad PrestamistaM
	//es la clave primaria y foránea al mismo tiempo.
	//columna de tabla tb_prestamista utiliza para unir las 2 tablas
	@JoinColumn(name="idprestamista")
	private PersonaM prestamista;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecharegistro")
	private Date fechaRegistro;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fechaedicion")
	private Date fechaEdicion;
	private boolean activo;
}//fin de PrestamistaM
