package com.imss.sivimss.contratocvpps.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReporteRequest {
	
	private Integer idOds;
	private String tipoReporte;
	private String rutaNombreReporte;
	
}
