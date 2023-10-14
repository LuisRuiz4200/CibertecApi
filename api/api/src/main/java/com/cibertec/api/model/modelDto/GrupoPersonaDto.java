package com.cibertec.api.model.modelDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GrupoPersonaDto {
    private int id;

    private int idPersona;
    private String nombres;
    private String apellidos;
    private String email;

    private int idGrupo;
    private String descripcion;
}
