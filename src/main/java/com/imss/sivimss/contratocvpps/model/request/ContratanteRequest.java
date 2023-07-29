package com.imss.sivimss.contratocvpps.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContratanteRequest extends Persona{

	private Integer idContratante;
	
	private String matricula;

	private DomicilioRequest cp;
	
}
