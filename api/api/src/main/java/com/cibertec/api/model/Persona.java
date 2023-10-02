package com.cibertec.api.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.gson.annotations.SerializedName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_persona")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="idpersona")
	private int idPersona;
	private String nombres;
	private String apellidos;
	private String direccion;
	private String email;
	private String dni;
	private String ruc;
	@Temporal(TemporalType.DATE)
	@Column(name="fecharegistro")
	private Date fechaRegistro;
	@Temporal(TemporalType.DATE)
	@Column(name="fechaedicion")
	private Date fechaEdicion;
	private boolean activo;
	
	
}
