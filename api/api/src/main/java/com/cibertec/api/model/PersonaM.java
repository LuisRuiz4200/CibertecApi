package com.cibertec.api.model;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tb_persona")
@AllArgsConstructor
@NoArgsConstructor

 @Getter
 @Setter
 @ToString
public class PersonaM {
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaRegistro;
	@Temporal(TemporalType.DATE)
	@Column(name="fechaedicion")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaEdicion;
	private boolean activo;
} //fin de PersonaM
