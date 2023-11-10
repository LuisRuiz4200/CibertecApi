package com.cibertec.api.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Comment;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name="tb_prestatario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Prestatario {
	@Id
    @Column(name = "idprestatario")
    private int idPrestatario;

	@ManyToOne
	@JoinColumn(name = "idPrestamista")
	private Prestamista prestamistaPrestatario;
	
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name="idprestatario")
	private Persona prestatario = new Persona(); ;
	@Temporal(TemporalType.DATE)
	@Column(name="fecharegistro")
	private Date fechaRegistro;
	@Temporal(TemporalType.DATE)
	@Column(name="fechaedicion")
	private Date fechaEdicion;
	private boolean activo;
	
	@OneToMany(mappedBy = "prestatario")
	@JsonIgnore
	@ToString.Exclude
	private List<SolicitudPrestamo> listaSolicitudPrestamo;
	
	@OneToMany(mappedBy = "idPrestatarioCuenta")
	@JsonIgnore
	@ToString.Exclude
    private List<Cuenta> cuentaList;
}
