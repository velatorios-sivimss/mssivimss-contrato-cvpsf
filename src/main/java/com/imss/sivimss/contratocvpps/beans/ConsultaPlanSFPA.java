package com.imss.sivimss.contratocvpps.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


import com.imss.sivimss.contratocvpps.model.request.ReporteRequest;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.ConsultaConstantes;
import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.SelectQueryUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsultaPlanSFPA   implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public DatosRequest consultaPlanSFPA(DatosRequest request, ReporteRequest reporteRequest) {
		log.info(" INICIO - consultaPlanSFPA");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SPLSFPA.ID_PLAN_SFPA AS ID_PLAN_SFPA","SPLSFPA.NUM_FOLIO_PLAN_SFPA AS NUM_FOLIO_PLAN_SFPA","CONCAT_WS(' ',SP.NOM_PERSONA,SP.NOM_PRIMER_APELLIDO,SP.NOM_SEGUNDO_APELLIDO ) AS TITULAR",
		"SD.DES_ESTADO AS ESTADO","SP.DES_CORREO AS CORREO_ELECTRONICO","SPE.DES_NOM_PAQUETE AS PAQUETE","TPM.DES_TIPO_PAGO_MENSUAL NUMERO_PAGO",
		"SEPLSFPA.DES_ESTATUS_PLAN_SFPA AS ESTATUS_PLAN_SFPA", "IFNULL(SEPA.DES_ESTATUS_PAGO_ANTICIPADO,'') AS ESTATUS_PAGO_ANTICIPADO")
		.from("SVT_PLAN_SFPA SPLSFPA").leftJoin("SVC_PAGO_SFPA SPSFPA", "SPLSFPA.ID_PLAN_SFPA = SPSFPA.ID_PLAN_SFPA")
		.innerJoin("SVC_CONTRATANTE SC", "SPLSFPA.ID_TITULAR = SC.ID_CONTRATANTE").innerJoin("SVC_PERSONA SP", "SP.ID_PERSONA = SC.ID_PERSONA")
		.innerJoin("SVT_DOMICILIO SD", "SD.ID_DOMICILIO = SC.ID_DOMICILIO").innerJoin("SVT_PAQUETE SPE", "SPE.ID_PAQUETE = SPLSFPA.ID_PAQUETE")
		.innerJoin("SVC_TIPO_PAGO_MENSUAL TPM", "TPM.ID_TIPO_PAGO_MENSUAL = SPLSFPA.ID_TIPO_PAGO_MENSUAL")
		.innerJoin("SVC_VELATORIO SV", "SV.ID_VELATORIO = SPLSFPA.ID_VELATORIO")
		.innerJoin("SVC_ESTATUS_PLAN_SFPA SEPLSFPA", "SEPLSFPA.ID_ESTATUS_PLAN_SFPA = SPLSFPA.ID_ESTATUS_PLAN_SFPA")
		.leftJoin("SVC_ESTATUS_PAGO_ANTICIPADO SEPA", "SEPA.ID_ESTATUS_PAGO_ANTICIPADO = SPSFPA.ID_ESTATUS_PAGO")
		.where("IFNULL(SPLSFPA.ID_PLAN_SFPA ,0) > 0");
		if(reporteRequest.getIdVelatorio() != null) {
			queryUtil.and("SPLSFPA.ID_VELATORIO IN ("+reporteRequest.getIdVelatorio()+")");
		}
		if(reporteRequest.getNumFolioPlanSfpa() != null) {
			queryUtil.and("SPLSFPA.NUM_FOLIO_PLAN_SFPA = :numFolioPlanSfpa").setParameter("numFolioPlanSfpa", reporteRequest.getNumFolioPlanSfpa());
		}
		if(reporteRequest.getRfc() != null) {
			queryUtil.and("SP.CVE_RFC = :rfc").setParameter("rfc", reporteRequest.getRfc());
		}
		if(reporteRequest.getCurp() != null) {
			queryUtil.and("SP.CVE_CURP = :curp").setParameter("curp", reporteRequest.getCurp());
		}
		if(reporteRequest.getNombreAfiliado() != null) {
			queryUtil.and("CONCAT_WS(' ',SP.NOM_PERSONA,SP.NOM_PRIMER_APELLIDO,SP.NOM_SEGUNDO_APELLIDO ) LIKE '%"+reporteRequest.getNombreAfiliado()+"%'");
		}
		if(reporteRequest.getIdEstatusPlanSfpa() != null) {
			queryUtil.and("SPLSFPA.ID_ESTATUS_PLAN_SFPA = :idEstatusPlanSfpa").setParameter("idEstatusPlanSfpa", reporteRequest.getIdEstatusPlanSfpa());
		}
		if(reporteRequest.getFechaInicio() != null && reporteRequest.getFechaFin() != null) {
			queryUtil.and("SPLSFPA.FEC_INGRESO  BETWEEN '"+reporteRequest.getFechaInicio()+"' AND '"+reporteRequest.getFechaFin()+"'");
		}
		queryUtil.orderBy("SPLSFPA.ID_PLAN_SFPA ASC");
		final String query = queryUtil.build();
		log.info(" consultaPlanSFPA: " + query);
		request.getDatos().put(AppConstantes.QUERY, ConsultaConstantes.queryEncoded(query));
		log.info(" TERMINO - consultaPlanSFPA");
		return request;
	}
	
	public String consultaPlanSFPA(ReporteRequest reporteRequest) {
		StringBuilder condicciones = new StringBuilder();
		
		if(reporteRequest.getIdVelatorio() != null) {
			condicciones.append(" AND SPLSFPA.ID_VELATORIO IN (").append(reporteRequest.getIdVelatorio()).append(")");
		}
		if(reporteRequest.getNumFolioPlanSfpa() != null) {
			condicciones.append(" AND SPLSFPA.NUM_FOLIO_PLAN_SFPA = '").append(reporteRequest.getNumFolioPlanSfpa()).append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		if(reporteRequest.getRfc() != null) {
			condicciones.append("AND SP.CVE_RFC = '").append(reporteRequest.getRfc()).append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		if(reporteRequest.getCurp() != null) {
			condicciones.append("AND SP.CVE_CURP = '").append(reporteRequest.getCurp()).append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		if(reporteRequest.getNombreAfiliado() != null) {
			condicciones.append(" AND CONCAT_WS(' ',SP.NOM_PERSONA,SP.NOM_PRIMER_APELLIDO,SP.NOM_SEGUNDO_APELLIDO ) LIKE '%").append(reporteRequest.getNombreAfiliado()).append("%'");
		}
		if(reporteRequest.getIdEstatusPlanSfpa() != null) {
			condicciones.append(" AND SPLSFPA.ID_ESTATUS_PLAN_SFPA = ").append(reporteRequest.getIdEstatusPlanSfpa());
		}
		if(reporteRequest.getFechaInicio() != null && reporteRequest.getFechaFin() != null) {
			condicciones.append(" AND SPLSFPA.FEC_INGRESO  BETWEEN '").append(reporteRequest.getFechaInicio()).append("' AND '").append(reporteRequest.getFechaFin()).append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		return condicciones.toString();
	}
	
	public Map<String, Object> generarReportePlanSFPA(ReporteRequest reporteRequest, String rutaNombreReporte) {
		Map<String, Object> envioDatos = new HashMap<>();
		String condicion = consultaPlanSFPA(reporteRequest);
		
		log.info("condicion::  " + condicion);
		log.info("tipoRepirte::  " + reporteRequest.getTipoReporte());
		
		envioDatos.put("condicion", condicion);
		envioDatos.put("tipoReporte", reporteRequest.getTipoReporte());
		envioDatos.put("rutaNombreReporte", rutaNombreReporte);

		return envioDatos;
	}

}
