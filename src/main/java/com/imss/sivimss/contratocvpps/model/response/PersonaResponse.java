package com.imss.sivimss.contratocvpps.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonaResponse {
	
	private Integer idContratante;
	private Integer idPersona;
	private Integer idDomicilio;

}
