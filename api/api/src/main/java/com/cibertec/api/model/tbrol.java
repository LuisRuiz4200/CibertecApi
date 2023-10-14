package com.cibertec.api.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tb_rol")
@AllArgsConstructor
@NoArgsConstructor
  @Getter
 @Setter
  @ToString
public class tbrol {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="idrol")
	private int idRol;
	private String descripcion;
	
	
	
	
}//fin de tbrol
