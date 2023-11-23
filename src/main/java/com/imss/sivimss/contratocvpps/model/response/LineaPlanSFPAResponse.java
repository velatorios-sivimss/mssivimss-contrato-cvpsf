package com.imss.sivimss.contratocvpps.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineaPlanSFPAResponse {
	
	private Integer idPlanSfpa;
	private String numFolioPlanSFPA;
	private String curpTitular;
	private String nombreTitular;
	private String curpSubtitular;
	private String nombreSubtitular;

}
