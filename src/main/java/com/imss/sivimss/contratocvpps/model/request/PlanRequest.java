package com.imss.sivimss.contratocvpps.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanRequest {
	private PlanSFPA plan;
	private PlanSFPA contratante;
	private PlanSFPA titularSubstituto;
	private PlanSFPA beneficiario1;
	private PlanSFPA beneficiario2;

}