package com.imss.sivimss.contratocvpps.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class IdentificacionRequest {

	@JsonProperty
	private String apMaterno;
	@JsonProperty
	private String apPaterno;
	@JsonProperty
	private String cDetSitCont;
	@JsonProperty
	private String cSitCont;
	@JsonProperty
	private String cSitContDom;
	@JsonProperty
	private String cSitDom;
	@JsonProperty
	private String cURP;
	@JsonProperty
	private String dSitCont;
	@JsonProperty
	private String dSitContDom;
	@JsonProperty
	private String dSitDom;
	@JsonProperty
	private String fConstitucion;
	@JsonProperty
	private String fIniOpers;
	@JsonProperty
	private String fNacimiento;
	@JsonProperty
	private String fSitCont;
	@JsonProperty
	private String nacionalidad;
	@JsonProperty
	private String nIT;
	@JsonProperty
	private String nombre;
	@JsonProperty
	private String nomComercial;
	@JsonProperty
	private String paisOrigen;
	@JsonProperty
	private String razonSoc;
	@JsonProperty
	private String tSociedad;

}
