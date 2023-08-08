package com.imss.sivimss.contratocvpps.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReporteRequest {
	
	private String idVelatorio;
	private String numFolioPlanSfpa;
	private String rfc;
	private String curp;
	private String nombreAfiliado;
	private Integer idEstatusPlanSfpa;
	private String fechaInicio;
	private String fechaFin;
	private String tipoReporte;
	
}
