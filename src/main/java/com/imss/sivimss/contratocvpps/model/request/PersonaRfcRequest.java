package com.imss.sivimss.contratocvpps.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PersonaRfcRequest {

	@JsonProperty
	List<IdentificacionRequest> identificacion;

	@JsonProperty
	List<UbicacionRequest> ubicacion;

}
