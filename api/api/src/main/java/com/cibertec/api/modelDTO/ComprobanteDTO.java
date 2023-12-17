package com.cibertec.api.modelDTO;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class ComprobanteDTO {

	private int idComprobante;
	private int idTipoComprobante;
	private String serie;
	private int correlativo;
	private Date fechaEmision;
	private String rucEmisor;
	private String nomEmisor;
	private Integer idPrestamo = null;
	private int idTipoDocumento;
	private int idPrestatario;
	private String numDocReceptor;
	private String nomReceptor;
	private String serieRef;
	private int correlativoRef;
	private Date fechaRegistro;
	private String estado;

	private List<ComprobanteDetalleDTO> listaComprobanteDetalle;

}
