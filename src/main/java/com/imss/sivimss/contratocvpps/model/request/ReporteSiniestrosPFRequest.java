package com.imss.sivimss.contratocvpps.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReporteSiniestrosPFRequest {
	
	private Integer id_delegacion;
	private Integer id_velatorio;
	private String des_velatorio;
	private String ods;
	private String fecha_inicial;
	private String fecha_final;
	private String id_tipo_reporte;

}