package com.cibertec.api.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class RendimientoDTO {
    private int idJefePrestamista;
    private int idPrestamista;
    private String nombrePrestamista;
    private double totalPrestado;
    private double totalInteres;
    private double totalPagado;
    private double totalPendiente;
    private double rentabilidad;
}
