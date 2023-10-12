package com.cibertec.api.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_grupo_prestamista")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GrupoPrestamista{
    
	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// private Long id;
	@EmbeddedId
    private GrupoPrestamistaId id;

	private Boolean activo;
}
