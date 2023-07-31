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
public class ServFunerariosPagoAnticipado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String SP_IND_ACTIVO_1 = "SP.IND_ACTIVO = 1";
	
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
		.from("SVC_TIPO_PAGO_MENSUAL STPM");
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
	
	public DatosRequest consultaPaquetes(DatosRequest request, UsuarioDto usuarioDto) {
		log.info(" INICIO - consultaPaquetes");
		SelectQueryUtil selectQueryUtilUnionPaqueteRegion= new SelectQueryUtil();
		SelectQueryUtil selectQueryUtilUnionPaqueteVelatorio= new SelectQueryUtil();
		selectQueryUtilUnionPaqueteVelatorio.select("SP.ID_PAQUETE","SP.DES_NOM_PAQUETE")
		.from("SVT_PAQUETE SP")
		.innerJoin("SVT_PAQUETE_VELATORIO SPV", "SP.ID_PAQUETE=SPV.ID_PAQUETE")
		.where(SP_IND_ACTIVO_1).and("SPV.ID_VELATORIO = :idVelatorio").setParameter("idVelatorio", usuarioDto.getIdVelatorio());

		selectQueryUtilUnionPaqueteRegion.select("SP.ID_PAQUETE","SP.DES_NOM_PAQUETE")
		.from("SVT_PAQUETE SP").where(SP_IND_ACTIVO_1).and("SP.IND_REGION = 1");
		
		final String query =  selectQueryUtilUnionPaqueteVelatorio.union(selectQueryUtilUnionPaqueteRegion);

		log.info(" consultaPaquetes: " + query);

		String encoded=DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - consultaPaquetes");
		return request;
	}
	
	public DatosRequest consultaFolioPlanSFPA(DatosRequest request, PlanSFPARequest planSFPARequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - consultaFolioPlanSFPA");
		SelectQueryUtil velatorio = new SelectQueryUtil();
		velatorio.select("SUBSTRING(UPPER(SV.DES_VELATORIO),1,3)").from("SVC_VELATORIO SV")
				.where("SV.ID_VELATORIO = :idVelatorio").setParameter("idVelatorio", usuarioDto.getIdVelatorio());
		
		log.info(velatorio.build());

		SelectQueryUtil spsfpa = new SelectQueryUtil();
		spsfpa.select("COUNT(*)").from(ConsultaConstantes.SVT_PLAN_SFPA_SPSFPA).where("SPSFPA.ID_VELATORIO = :idVelatorio").setParameter("idVelatorio", usuarioDto.getIdVelatorio())
				.and("SPSFPA.ID_ESTATUS_PLAN_SFPA not in (6)").and("SPSFPA.NUM_FOLIO_PLAN_SFPA IS NOT NULL");
		
		log.info(spsfpa.build());

		SelectQueryUtil spsfpaCount = new SelectQueryUtil();
		spsfpaCount.select("COUNT(*)+1").from(ConsultaConstantes.SVT_PLAN_SFPA_SPSFPA).where("SPSFPA.ID_VELATORIO = :idVelatorio").setParameter("idVelatorio", usuarioDto.getIdVelatorio())
				.and("SPSFPA.ID_ESTATUS_PLAN_SFPA not in (6)").and("SPSFPA.NUM_FOLIO_PLAN_SFPA IS NOT NULL");
		
		log.info(spsfpaCount.build());

		SelectQueryUtil selectQueryUtil = new SelectQueryUtil();
		selectQueryUtil
				.select("CONCAT((" + velatorio.build() + ")", "'-'", "LPAD((case when (" + spsfpa.build()
						+ ") = 0 then 1 else (" + spsfpaCount.build() + ") end)" + ",6,'0')" + ") as numFolioPlanSFPA")
				.from("dual");
		final String query = selectQueryUtil.build();
		log.info(" consultaFolioPlanSFPA: " + query);
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - consultaFolioPlanSFPA");
		return request;
	}
	
	public DatosRequest consultaExistePersona(DatosRequest request, ContratanteRequest contratanteRequest) {
		log.info(" INICIO - consultaExistePersona");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SC.ID_CONTRATANTE AS idContratante","SC.ID_DOMICILIO AS idDomicilio","SC.ID_PERSONA AS idPersona")
		.from("SVC_CONTRATANTE SC")
		.innerJoin("SVC_PERSONA SP", "SP.ID_PERSONA = SC.ID_PERSONA")
		.innerJoin("SVT_DOMICILIO SD", "SD.ID_DOMICILIO = SC.ID_DOMICILIO")
		.where("SP.CVE_CURP = :curp").setParameter("curp", contratanteRequest.getCurp()).or("SP.CVE_RFC = :rfc").setParameter("rfc", contratanteRequest.getRfc())
		.or("SP.NUM_INE = :ine").setParameter("ine", contratanteRequest.getIne());
		final String query = queryUtil.build();
		log.info(" consultaExistePersona: " + query);
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - consultaExistePersona");
		return request;
	}
	
	public DatosRequest consultaValidaAfiliado(DatosRequest request, ContratanteRequest contratanteRequest) {
		log.info(" INICIO - consultaValidaAfiliado");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SPSFPA.NUM_FOLIO_PLAN_SFPA as NUM_FOLIO_PLAN_SFPA")
		.from(ConsultaConstantes.SVT_PLAN_SFPA_SPSFPA)
		.where("SPSFPA.ID_TITULAR = (SELECT SC.ID_CONTRATANTE AS IDCONTRATANTE".concat(" FROM SVC_CONTRATANTE SC ")
		.concat(" INNER JOIN SVC_PERSONA SP ON SP.ID_PERSONA = SC.ID_PERSONA").concat(" INNER JOIN SVT_DOMICILIO SD ON SD.ID_DOMICILIO = SC.ID_DOMICILIO ")
		.concat("WHERE SP.CVE_CURP = '")+contratanteRequest.getCurp()+"' OR SP.CVE_RFC = '"+ contratanteRequest.getRfc()+"' OR SP.NUM_INE = '" +contratanteRequest.getIne()+"')")
		.and("SPSFPA.ID_ESTATUS_PLAN_SFPA NOT IN (6)");
		final String query = queryUtil.build();
		log.info(" consultaValidaAfiliado: " + query);
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
		request.getDatos().put(AppConstantes.QUERY, encoded);
		log.info(" TERMINO - consultaValidaAfiliado");
		return request;
	}
	
	public String consultaNumeroPago(Integer idPlanSfpa) {
		log.info(" INICIO - consultaNumeroPago");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("COUNT(*) AS PAGO")
		.from("SVC_PAGO_SFPA PSFPA")
		.leftJoin("SVT_PLAN_SFPA PLSFPA", "PSFPA.ID_PLAN_SFPA = PLSFPA.ID_PLAN_SFPA")
		.and("PLSFPA.ID_ESTATUS_PLAN_SFPA NOT IN (6)").and("PLSFPA.IND_ACTIVO = 1")
		.where("PSFPA.ID_PLAN_SFPA = :idPlanSfpa").setParameter("idPlanSfpa", idPlanSfpa)
		.and("PSFPA.IND_ACTIVO = 1");
		final String query = queryUtil.build();
		log.info(" consultaNumeroPago: " + query);
		log.info(" TERMINO - consultaNumeroPago");
		return query;
	}
}
