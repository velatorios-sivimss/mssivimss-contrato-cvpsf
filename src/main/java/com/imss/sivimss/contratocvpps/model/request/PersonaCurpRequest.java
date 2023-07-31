package com.imss.sivimss.contratocvpps.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PersonaCurpRequest {

	@JsonProperty
	private String anioReg;
	@JsonProperty
	private String apellido1;
	@JsonProperty
	private String apellido2;
	@JsonProperty
	private String codigoError;
	@JsonProperty
	private String cRIP;
	@JsonProperty
	private String curp;
	@JsonProperty
	private String curpsHistoricas;
	@JsonProperty
	private String cveEntidadEmisora;
	@JsonProperty
	private String cveEntidadNac;
	@JsonProperty
	private String cveMunicipioReg;
	@JsonProperty
	private String desEntidadNac;
	@JsonProperty
	private String desEntidadRegistro;
	@JsonProperty
	private String desEstatusCURP;
	@JsonProperty
	private String desMunicipio;
	@JsonProperty
	private String docProbatorio;
	@JsonProperty
	private String estatusCURP;
	@JsonProperty
	private String fechNac;
	@JsonProperty
	private String foja;
	@JsonProperty
	private String folioCarta;
	@JsonProperty
	private String libro;
	@JsonProperty
	private String message;
	@JsonProperty
	private String nacionalidad;
	@JsonProperty
	private String nombre;
	@JsonProperty
	private String numActa;
	@JsonProperty
	private String numEntidadReg;
	@JsonProperty
	private String numRegExtranjeros;
	@JsonProperty
	private String sexo;
	@JsonProperty
	private String statusOper;
	@JsonProperty
	private String tipoError;
	@JsonProperty
	private String tomo;

}
