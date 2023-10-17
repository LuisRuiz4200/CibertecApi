package com.cibertec.api.model;

import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
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
public class Prestamista {
	@Id
    @Column(name = "idprestamista")
    private int idPrestamista;
	//entidad Prestamista 1 by 1 Persona
	//@OneToOne(cascade = CascadeType.ALL) //Engloba a todos
	@OneToOne(cascade = {CascadeType.ALL})
	@MapsId //indica que el atributo idPrestamista de la entidad Prestamista
	//es la clave primaria y foránea al mismo tiempo.
	//columna de tabla tb_prestamista utiliza para unir las 2 tablas
	@JoinColumn(name="idprestamista")
	private Persona prestamista = new Persona();
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecharegistro")
	private Date fechaRegistro;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fechaedicion")
	private Date fechaEdicion;
	private boolean activo;
//	@OneToMany(mappedBy = "prestamista")
//	@JsonIgnore
//	private List<SolicitudPrestamo> listaSolicitudPrestamo;

	@OneToMany
	private List<GrupoPrestamista> idJefePrestamista;

	@OneToMany
	private List<GrupoPrestamista> idAsesorPrestamista;
}//fin de PrestamistaM
