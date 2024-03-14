package com.imss.sivimss.contratocvpps.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestFiltroPaginado {	
	@Min(value=0,message = "Pagina Minima 0")
	private Integer pagina;
	
	@Min(value=1,message = "Tamanio Minimo 1")
	private Integer tamanio;

	@Min(value=1,message = "Tamanio Minimo 1")
	private Integer idVelatorio;

	private String numFolioPlanSfpa;
	
	private String rfc;
	
	private String curp;

	private String nombreAfiliado;
	
	private Integer idEstatusPlanSfpa;

	private String fechaInicio;
	
	private String fechaFin;
	
	
	
}
