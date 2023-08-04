package com.imss.sivimss.contratocvpps.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanSFPARequest {
	
	private Integer idPlanSfpa;
	private String numFolioPlanSfpa;
	private Integer idTipoContratacion;
	private Integer idPaquete;
	private Double monPrecio;
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
