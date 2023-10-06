package com.cibertec.api.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="tb_prestamista")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prestamista implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "idprestamista")
    private int idPrestamista;
	
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name="idprestamista")
	private Persona prestamista = new Persona() ;
	@Temporal(TemporalType.DATE)
	@Column(name="fecharegistro")
	private Date fechaRegistro;
	@Temporal(TemporalType.DATE)
	@Column(name="fechaedicion")
	private Date fechaEdicion;
	private boolean activo;
	@OneToMany(mappedBy = "prestamista")
	@JsonIgnore
	private List<GrupoPrestamista> listaGrupoPrestamista;
}
