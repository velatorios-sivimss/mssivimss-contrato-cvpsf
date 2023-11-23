package com.imss.sivimss.contratocvpps.beans;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.DatatypeConverter;

import com.imss.sivimss.contratocvpps.model.request.ContratanteRequest;
import com.imss.sivimss.contratocvpps.model.request.PlanSFPARequest;
import com.imss.sivimss.contratocvpps.model.request.TitularRequest;
import com.imss.sivimss.contratocvpps.model.request.UsuarioDto;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.ConsultaConstantes;
import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.SelectQueryUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlanSFPA implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String SVC_TIPO_PAGO_MENSUAL_STPM = "SVC_TIPO_PAGO_MENSUAL STPM";
	
	private static final String SVT_PAQUETE_SP = "SVT_PAQUETE SP";
	
	private static final String SP_IND_ACTIVO_1 = "SP.IND_ACTIVO = 1";
	
	private static final String ID_VELATORIO = "idVelatorio";
	
	public DatosRequest detalleContratanteRfc(DatosRequest request, TitularRequest titularRequest ) {
		log.info(" INICIO - detalleContratanteRfc");
		String query = ConsultaConstantes.detalleContratante().where("P.CVE_RFC = :rfc").setParameter("rfc", titularRequest.getRfc()).build();
		log.info(" detalleContratanteRfc: " + query );
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - detalleContratanteRfc");
		return request;
	}
	
	public DatosRequest detalleContratanteCurp(DatosRequest request, TitularRequest titularRequest ) {
		log.info(" INICIO - detalleContratanteCurp");
		String query = ConsultaConstantes.detalleContratante().where("P.CVE_CURP = :curp").setParameter("curp", titularRequest.getCurp()).build();
		log.info(" detalleContratanteCurp: " + query );
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - detalleContratanteCurp");
		return request;
	}
	
	public DatosRequest consultaTipoContratacion(DatosRequest request) {
		log.info(" INICIO - consultaTipoContratacion");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("STC.ID_TIPO_CONTRATACION AS ID_TIPO_CONTRATACION","STC.DES_TIPO_CONTRATACION AS DES_TIPO_CONTRATACION")
		.from("SVC_TIPO_CONTRATACION STC");
		final String query = queryUtil.build();
		log.info(" consultaTipoContratacion: " + query);
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - consultaTipoContratacion");
		return request;
	}
	
	public DatosRequest consultaTipoPagoMensual(DatosRequest request) {
		log.info(" INICIO - consultaTipoPagoMensual");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("STPM.ID_TIPO_PAGO_MENSUAL AS ID_TIPO_PAGO_MENSUAL","STPM.DES_TIPO_PAGO_MENSUAL AS DES_TIPO_PAGO_MENSUAL")
		.from(SVC_TIPO_PAGO_MENSUAL_STPM);
		final String query = queryUtil.build();
		log.info(" consultaTipoPagoMensual: " + query);
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - consultaTipoPagoMensual");
		return request;
	}
	
	public DatosRequest consultaPromotores(DatosRequest request) {
		log.info(" INICIO - consultaPromotores");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SP.ID_PROMOTOR AS ID_PROMOTOR","CONCAT(IFNULL(SP.NOM_PROMOTOR,''),' ',IFNULL(SP.NOM_PAPELLIDO,''),' ',IFNULL(SP.NOM_SAPELLIDO,'')) AS NOMBRE")
		.from("SVT_PROMOTOR SP").where(SP_IND_ACTIVO_1);
		final String query = queryUtil.build();
		log.info(" consultaPromotores: " + query);
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - consultaPromotores");
		return request;
	}
	
	public DatosRequest consultaPaquetes(DatosRequest request) {
		log.info(" INICIO - consultaPaquetes");
		SelectQueryUtil selectQueryVelatorio= new SelectQueryUtil();
		selectQueryVelatorio.select("SP.ID_PAQUETE AS idPaquete","SP.REF_PAQUETE_NOMBRE AS nomPaquete","SP.REF_PAQUETE_DESCRIPCION AS descPaquete","IFNULL(SP.MON_PRECIO,0) as monPrecio")
		.from(SVT_PAQUETE_SP).where("IFNULL(SP.ID_PAQUETE ,0) > 0").and(SP_IND_ACTIVO_1);
		
		final String query =  selectQueryVelatorio.build();

		log.info(" consultaPaquetes: " + query);

		String encoded=DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - consultaPaquetes");
		return request;
	}
	
	public String obtenerFolioPlanSFPA(PlanSFPARequest planSFPARequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - consultaFolioPlanSFPA");
		SelectQueryUtil velatorio = new SelectQueryUtil();
		velatorio.select("SUBSTRING(UPPER(SV.DES_VELATORIO),1,3)").from("SVC_VELATORIO SV")
				.where("SV.ID_VELATORIO = :idVelatorio").setParameter(ID_VELATORIO, usuarioDto.getIdVelatorio());
		
		SelectQueryUtil paquete = new SelectQueryUtil();
		paquete.select("SUBSTRING(UPPER(SP.REF_PAQUETE_NOMBRE),1,3)").from(SVT_PAQUETE_SP)
				.where("SP.ID_PAQUETE = :idPaquete").setParameter("idPaquete", planSFPARequest.getIdPaquete());
		
		SelectQueryUtil numeroPagoMensual = new SelectQueryUtil();
		numeroPagoMensual.select("STPM.DES_TIPO_PAGO_MENSUAL").from(SVC_TIPO_PAGO_MENSUAL_STPM)
				.where("STPM.ID_TIPO_PAGO_MENSUAL = :idTipoPagoMensual").setParameter("idTipoPagoMensual", planSFPARequest.getIdTipoPagoMensual());
		
		SelectQueryUtil numeroConsecutivo  = new SelectQueryUtil();
		numeroConsecutivo .select("IFNULL(MAX(SPSFPA.ID_PLAN_SFPA),0) + 1").from("SVT_PLAN_SFPA SPSFPA");

		SelectQueryUtil selectQueryUtil = new SelectQueryUtil();
		selectQueryUtil
				.select("CONCAT_WS('-',(" + velatorio.build() + "),("+ paquete.build() +"),("+ numeroPagoMensual.build() +"),("+ numeroConsecutivo.build() +"))")
				.from("DUAL )");
		final String query = selectQueryUtil.build();
		log.info(" TERMINO - consultaFolioPlanSFPA " + query);
		return query;
	}
	
	public String consultaExisteTitularBeneficiarios(ContratanteRequest contratanteRequest) {
		log.info(" INICIO - consultaExisteTitularBeneficiarios");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("STB.ID_TITULAR_BENEFICIARIOS AS idTitularBeneficiarios","SD.ID_DOMICILIO AS idDomicilio","SP.ID_PERSONA AS idPersona")
		.from("SVC_PERSONA SP")
		.leftJoin("SVT_TITULAR_BENEFICIARIOS STB", "SP.ID_PERSONA = STB.ID_PERSONA")
		.leftJoin("SVT_DOMICILIO SD", "SD.ID_DOMICILIO = STB.ID_DOMICILIO")
		.where("IFNULL(SP.ID_PERSONA ,0) > 0");
		if(contratanteRequest.getCurp() != null && !contratanteRequest.getCurp().isEmpty()) {
			queryUtil.and("SP.CVE_CURP = :curp").setParameter("curp", contratanteRequest.getCurp());
		}
		if(contratanteRequest.getRfc() != null && !contratanteRequest.getRfc().isEmpty()) {
			queryUtil.and("SP.CVE_RFC = :rfc").setParameter("rfc", contratanteRequest.getRfc());
		}
		if (contratanteRequest.getIne() != null ) {
			queryUtil.and("SP.NUM_INE = :ine").setParameter("ine", contratanteRequest.getIne());
		}
		queryUtil.orderBy("SP.ID_PERSONA DESC LIMIT 1");
		final String query = queryUtil.build();
		log.info(" TERMINO - consultaExisteTitularBeneficiarios ");
		return query;
	}
	
	public String consultaExisteContratante(ContratanteRequest contratanteRequest) {
		log.info(" INICIO - consultaExisteContratante");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SC.ID_CONTRATANTE AS idContratante","SD.ID_DOMICILIO AS idDomicilio","SP.ID_PERSONA AS idPersona")
		.from("SVC_PERSONA SP")
		.leftJoin("SVC_CONTRATANTE SC", "SP.ID_PERSONA = SC.ID_PERSONA")
		.leftJoin("SVT_DOMICILIO SD", "SD.ID_DOMICILIO = SC.ID_DOMICILIO")
		.where("IFNULL(SP.ID_PERSONA , 0) > 0");
		if(contratanteRequest.getCurp() != null && !contratanteRequest.getCurp().isEmpty()) {
			queryUtil.and("SP.CVE_CURP = :curp").setParameter("curp", contratanteRequest.getCurp());
		}
		if(contratanteRequest.getRfc() != null && !contratanteRequest.getRfc().isEmpty()) {
			queryUtil.and("SP.CVE_RFC = :rfc").setParameter("rfc", contratanteRequest.getRfc());
		}
		if (contratanteRequest.getIne() != null ) {
			queryUtil.and("SP.NUM_INE = :ine").setParameter("ine", contratanteRequest.getIne());
		}
		queryUtil.orderBy("SP.ID_PERSONA DESC LIMIT 1");
		final String query = queryUtil.build();
		log.info(" TERMINO - consultaExisteContratante ");
		return query;
	}
	
	public DatosRequest consultaValidaAfiliado(DatosRequest request, ContratanteRequest contratanteRequest) {
		log.info(" INICIO - consultaValidaAfiliado");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		StringBuilder subQuery = new StringBuilder();
		if(contratanteRequest.getCurp() != null) {
			subQuery.append(" AND SP.CVE_CURP = '").append(contratanteRequest.getCurp()).append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		if(contratanteRequest.getRfc() != null) {
			subQuery.append(" AND SP.CVE_RFC = '").append(contratanteRequest.getRfc()).append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		if(contratanteRequest.getIne() != null) {
			subQuery.append(" AND SP.NUM_INE = '").append(contratanteRequest.getIne()).append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		queryUtil.select("SPSFPA.ID_PLAN_SFPA AS ID_PLAN_SFPA","SPSFPA.NUM_FOLIO_PLAN_SFPA AS NUM_FOLIO_PLAN_SFPA")
		.from(ConsultaConstantes.SVT_PLAN_SFPA_SPSFPA)
		.where("SPSFPA.ID_TITULAR = (SELECT SC.ID_CONTRATANTE AS IDCONTRATANTE".concat(" FROM SVC_CONTRATANTE SC ")
		.concat(" INNER JOIN SVC_PERSONA SP ON SP.ID_PERSONA = SC.ID_PERSONA").concat(" INNER JOIN SVT_DOMICILIO SD ON SD.ID_DOMICILIO = SC.ID_DOMICILIO ")
		.concat(" WHERE IFNULL(SC.ID_CONTRATANTE ,0) > 0 ").concat(subQuery.toString()).concat(" ORDER BY SC.ID_CONTRATANTE DESC LIMIT 1").concat(")"))
		.and("SPSFPA.ID_ESTATUS_PLAN_SFPA NOT IN (6)");
		final String query = queryUtil.build();
		log.info(" consultaValidaAfiliado: " + query);
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - consultaValidaAfiliado");
		return request;
	}
	
	public String folioPlanSfpa(Integer idPlanSfpa) {
		log.info(" INICIO - folioPlanSfpa");
		StringBuilder query = new StringBuilder();
		query.append(" SELECT NUM_FOLIO_PLAN_SFPA AS folioPlanSFPA FROM ").append(ConsultaConstantes.SVT_PLAN_SFPA_SPSFPA).append(" WHERE SPSFPA.ID_PLAN_SFPA = ").append(idPlanSfpa);
		log.info(" TERMINO - folioPlanSfpa" + query);
		return query.toString();
	}
	
	public String consultarPagoSfpa(PlanSFPARequest planSFPARequest) {
		log.info(" INICIO - consultarPagoSfpa");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("DATE_FORMAT(PSFPA.FEC_PARCIALIDAD,'%d/%m/%Y') AS FEC_PARCIALIDAD", "DATE_FORMAT(PSFPA.FEC_ALTA, '%d/%m/%Y') as FEC_ALTA")
		.from("SVT_PAGO_SFPA PSFPA").where("IFNULL(PSFPA.ID_PAGO_SFPA ,0) > 0")
		.and("PSFPA.ID_ESTATUS_PAGO = 8")
		.and("PSFPA.ID_PLAN_SFPA = :idPlanSfpa").setParameter("idPlanSfpa", planSFPARequest.getIdPlanSfpa())
		.orderBy("PSFPA.ID_PAGO_SFPA  ASC LIMIT 1");
		final String query = queryUtil.build();
		log.info(" TERMINO - consultarPagoSfpa: "  + query);
		return query;
	}
	
	public String eliminarPagoSfpa(PlanSFPARequest planSFPARequest) {
		log.info(" INICIO - deletePagoSfpa");
		StringBuilder delete = new StringBuilder();
		delete.append("DELETE FROM SVT_PAGO_SFPA WHERE ID_PLAN_SFPA = ").append(planSFPARequest.getIdPlanSfpa());
		log.info(" TERMINO - deletePagoSfpa: "  + delete);
		return delete.toString();
	}
	
	public String consultaPlanSFPA(Integer idPlanSfpa) {
		log.info(" INICIO - consultaPlanSFPA");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SPSFPA.ID_TITULAR as idTitular","SP.ID_PERSONA AS idPersona","IFNULL(SP.NOM_PERSONA, '') AS nomPersona","IFNULL(SP.NOM_PRIMER_APELLIDO, '') AS nomApellidoPaterno",
		"IFNULL(SP.NOM_SEGUNDO_APELLIDO, '') AS nomApellidoMaterno","IFNULL(SP.REF_CORREO , '') AS correo").from("SVT_PLAN_SFPA SPSFPA")
		.innerJoin("SVC_CONTRATANTE SC", "SC.ID_CONTRATANTE = SPSFPA.ID_TITULAR").innerJoin("SVC_PERSONA", "SP.ID_PERSONA = SC.ID_PERSONA")
		.where("IFNULL(SPSFPA.ID_PLAN_SFPA ,0) > 0").and("SPSFPA.ID_PLAN_SFPA = :idPlanSfpa").setParameter("idPlanSfpa", idPlanSfpa);
		final String query = queryUtil.build();
		log.info(" TERMINO - consultaPlanSFPA"+ query);
		return query;
	}
}
