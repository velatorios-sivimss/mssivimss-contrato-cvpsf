package com.imss.sivimss.contratocvpps.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomicilioRequest {
	
	private Integer idDomicilio;
	
	private String desCalle;

	private String numExterior;

	private String numInterior;
	
	private Integer codigoPostal;

	private String desColonia;

	private String desMunicipio;

	private String desEstado;

	public DomicilioRequest(Integer idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

}
