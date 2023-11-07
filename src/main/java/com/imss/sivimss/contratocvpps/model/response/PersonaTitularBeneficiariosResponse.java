package com.imss.sivimss.contratocvpps.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonaTitularBeneficiariosResponse {
	
	private Integer idTitularBeneficiarios = 0;
	private Integer idPersona = 0;
	private Integer idDomicilio = 0;

}
