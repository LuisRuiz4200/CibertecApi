package com.cibertec.api.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="tb_prestatario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prestatario {
	@Id
    @Column(name = "idprestatario")
	@JsonIgnore
    private int idPrestatario;
	
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name="idprestatario")
	private Persona prestatario ;
	@Temporal(TemporalType.DATE)
	@Column(name="fecharegistro")
	private Date fechaRegistro;
	@Temporal(TemporalType.DATE)
	@Column(name="fechaedicion")
	private Date fechaEdicion;
	private boolean activo;
	
}
