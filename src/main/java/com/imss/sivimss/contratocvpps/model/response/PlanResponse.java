package com.imss.sivimss.contratocvpps.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imss.sivimss.contratocvpps.model.request.PlanSFPA;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanResponse {
	private Integer idPlan;
	private Integer pagos;
	private PlanSFPA contratante;
	private PlanSFPA titularSubstituto;
	private PlanSFPA beneficiario1;
	private PlanSFPA beneficiario2;

}
