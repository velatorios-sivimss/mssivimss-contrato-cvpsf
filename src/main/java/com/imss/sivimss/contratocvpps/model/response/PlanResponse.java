package com.imss.sivimss.contratocvpps.model.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imss.sivimss.contratocvpps.model.request.PlanSFPA;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanResponse {
	private  Map<String, Object> plan;
	private  Map<String, Object> contratante;
	private  Map<String, Object> titularSubstituto;
	private  Map<String, Object> beneficiario1;
	private  Map<String, Object> beneficiario2;

}
