package com.imss.sivimss.contratocvpps.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imss.sivimss.contratocvpps.model.request.ContratanteRequest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanSFPAResponse {
	
	private Integer idPlanSfpa;
	private String numFolioPlanSFPA;
	private Integer idTipoContratacion;
	private Integer idPaquete;
	private Integer idTipoPagoMensual;
	private Integer indTitularSubstituto;
	private Integer indModificarTitularSubstituto;
	private Integer indPromotor;
	private Integer idPromotor;
	private Integer idVelatorio;
	private Integer idEstatusPlanSfpa;
	private Integer indActivo;
	private Boolean indTipoPagoMensual;
	private List<ContratanteRequest> titularesBeneficiarios;

}
