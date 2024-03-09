package com.imss.sivimss.contratocvpps.beans;

import java.io.Serializable;

import com.imss.sivimss.contratocvpps.util.SelectQueryUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DetallePlanSfpa implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public String consultaDetallePlanSfpa(Integer idPlanSfpa) {
		log.info(" INICIO - consultaDetallePlanSfpa");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SPSFPA.ID_PLAN_SFPA as idPlanSfpa", "SPSFPA.NUM_FOLIO_PLAN_SFPA as numFolioPlanSfpa",
				"SPSFPA.ID_TIPO_CONTRATACION as idTipoContratacion",
				"SPSFPA.ID_PAQUETE as idPaquete", "SPSFPA.ID_TIPO_PAGO_MENSUAL as idTipoPagoMensual",
				"SPSFPA.IND_TITULAR_SUBSTITUTO as indTitularSubstituto",
				"SPSFPA.IND_MODIF_TITULAR_SUB as indModificarTitularSubstituto", "SP.CVE_RFC as rfc",
				"SP.CVE_CURP as curp", "SC.CVE_MATRICULA as matricula",
				"SP.CVE_NSS as nss", "SP.NOM_PERSONA as nomPersona", "SP.NOM_PRIMER_APELLIDO as primerApellido",
				"SP.NOM_SEGUNDO_APELLIDO as segundoApellido",
				"SP.NUM_SEXO as sexoi",
				"CASE WHEN SP.NUM_SEXO = 1 THEN 'Mujer' WHEN SP.NUM_SEXO = 2 THEN 'Hombre' WHEN SP.NUM_SEXO = 3 THEN SP.REF_OTRO_SEXO  ELSE '' END  AS sexo",
				"SP.REF_OTRO_SEXO as otroSexo", "SP.FEC_NAC as fecNacimiento", "SP.ID_PAIS as idPais",
				"p.DES_PAIS AS pais",
				"SP.ID_ESTADO as idEstado",
				"e.DES_ESTADO AS estado",
				"SP.REF_TELEFONO as telefono", "SP.REF_TELEFONO_FIJO as telefonoFijo", "SP.REF_CORREO as correo",
				"SP.TIP_PERSONA as tipoPersona", "SP.NUM_INE as ine",
				"SD.REF_CALLE as desCalle", "SD.NUM_EXTERIOR as numExterior", "SD.NUM_INTERIOR as numInterior",
				"SD.REF_CP as codigoPostal", "SD.REF_COLONIA as desColonia",
				"SD.REF_MUNICIPIO as desMunicipio", "SD.REF_ESTADO as desEstado", "SP2.CVE_RFC as rfc",
				"SP2.CVE_CURP as curp", "STB2.CVE_MATRICULA as matricula",
				"SP2.CVE_NSS as nss", "SP2.NOM_PERSONA as nomPersona", "SP2.NOM_PRIMER_APELLIDO as primerApellido",
				"SP2.NOM_SEGUNDO_APELLIDO as segundoApellido",
				"SP2.NUM_SEXO as sexo",
				"CASE WHEN SP2.NUM_SEXO = 1 THEN 'Mujer' WHEN SP2.NUM_SEXO = 2 THEN 'Hombre' WHEN SP2.NUM_SEXO = 3 THEN SP2.REF_OTRO_SEXO ELSE '' END  AS sexo",
				"SP2.REF_OTRO_SEXO as otroSexo", "SP2.FEC_NAC as fecNacimiento", "SP2.ID_PAIS as idPais",
				"p2.DES_PAIS AS pais",
				"SP2.ID_ESTADO as idEstado",
				"e2.DES_ESTADO AS estado",
				"SP2.REF_TELEFONO as telefono", "SP2.REF_TELEFONO_FIJO as telefonoFijo", "SP2.REF_CORREO as correo",
				"SP2.TIP_PERSONA as tipoPersona", "SP2.NUM_INE as ine",
				"SD2.REF_CALLE as desCalle", "SD2.NUM_EXTERIOR as numExterior", "SD2.NUM_INTERIOR as numInterior",
				"SD2.REF_CP as codigoPostal", "SD2.REF_COLONIA as desColonia",
				"SD2.REF_MUNICIPIO as desMunicipio", "SD2.REF_ESTADO as desEstado", "SV.DES_VELATORIO",
				"DATE_FORMAT(STR_TO_DATE(SPSFPA.FEC_INGRESO,'%Y-%m-%d'),'%d/%m/%Y')",
				"(SELECT COUNT(*) FROM SVT_PAGO_SFPA PSFPA INNER JOIN SVT_PLAN_SFPA PLSFPA ON PSFPA.ID_PLAN_SFPA = PLSFPA.ID_PLAN_SFPA AND PSFPA.ID_ESTATUS_PAGO = 4 AND PLSFPA.ID_ESTATUS_PLAN_SFPA NOT IN (6) "
						.concat("AND PLSFPA.IND_ACTIVO = 1 WHERE PSFPA.ID_PLAN_SFPA =  ") + idPlanSfpa
						+ " AND PSFPA.IND_ACTIVO = 1) AS PAGO",
				"SP.ID_PERSONA as idPersona", "SD.ID_DOMICILIO as idDomicilio",
				"SP2.ID_PERSONA as idPersona", "SD2.ID_DOMICILIO as idDomicilio", "SPSFPA.ID_PROMOTOR as idPromotor",
				"SPSFPA.IND_PROMOTOR as indPromotor", "SPSFPA.IND_ACTIVO as indActivo",
				"SPSFPA.ID_VELATORIO as idVelatorio",
				"SP3.ID_PERSONA as idPersona", "SP3.CVE_RFC as rfc", "SP3.CVE_CURP as curp",
				"STB3.CVE_MATRICULA as matricula", "SP3.CVE_NSS as nss", "SP3.NOM_PERSONA as nomPersona",
				"SP3.NOM_PRIMER_APELLIDO as primerApellido", "SP3.NOM_SEGUNDO_APELLIDO as segundoApellido",
				"SP3.NUM_SEXO as sexoi",
				"CASE WHEN SP3.NUM_SEXO = 1 THEN 'Mujer' WHEN SP3.NUM_SEXO = 2 THEN 'Hombre' WHEN SP3.NUM_SEXO = 3 THEN SP3.REF_OTRO_SEXO ELSE '' END  AS sexo",
				"SP3.REF_OTRO_SEXO as otroSexo", "SP3.FEC_NAC as fecNacimiento",
				"SP3.ID_PAIS as idPais",
				"p3.DES_PAIS AS pais",
				"SP3.ID_ESTADO as idEstado",
				"e3.DES_ESTADO AS estado",
				"SP3.REF_TELEFONO as telefono", "SP3.REF_TELEFONO_FIJO as telefonoFijo", "SP3.REF_CORREO as correo",
				"SP3.TIP_PERSONA as tipoPersona",
				"SP3.NUM_INE as ine", "SD3.ID_DOMICILIO as idDomicilio", "SD3.REF_CALLE as desCalle",
				"SD3.NUM_EXTERIOR as numExterior", "SD3.NUM_INTERIOR as numInterior", "SD3.REF_CP as codigoPostal",
				"SD3.REF_COLONIA as desColonia",
				"SD3.REF_MUNICIPIO as desMunicipio", "SD3.REF_ESTADO as desEstado", "SP4.ID_PERSONA as idPersona",
				"SP4.CVE_RFC as rfc", "SP4.CVE_CURP as curp", "STB4.CVE_MATRICULA as matricula", "SP4.CVE_NSS as nss",
				"SP4.NOM_PERSONA as nomPersona",
				"SP4.NOM_PRIMER_APELLIDO as primerApellido", "SP4.NOM_SEGUNDO_APELLIDO as segundoApellido",
				"SP4.NUM_SEXO as sexo",
				"CASE WHEN SP4.NUM_SEXO = 1 THEN 'Mujer' WHEN SP4.NUM_SEXO = 2 THEN 'Hombre' WHEN SP4.NUM_SEXO = 3 THEN SP4.REF_OTRO_SEXO ELSE '' END  AS sexo",
				"SP4.REF_OTRO_SEXO as otroSexo", "SP4.FEC_NAC as fecNacimiento",
				"SP4.ID_PAIS as idPais",
				"p4.DES_PAIS AS pais",
				"SP4.ID_ESTADO as idEstado",
				"e4.DES_ESTADO AS estado",
				"SP4.REF_TELEFONO as telefono", "SP4.REF_TELEFONO_FIJO as telefonoFijo", "SP4.REF_CORREO as correo",
				"SP4.TIP_PERSONA as tipoPersona",
				"SP4.NUM_INE as ine", "SD4.ID_DOMICILIO as idDomicilio", "SD4.REF_CALLE as desCalle",
				"SD4.NUM_EXTERIOR as numExterior", "SD4.NUM_INTERIOR as numInterior", "SD4.REF_CP as codigoPostal",
				"SD4.REF_COLONIA as desColonia",
				"SD4.REF_MUNICIPIO as desMunicipio", "SD4.REF_ESTADO as desEstado")
				.from("SVT_PLAN_SFPA SPSFPA").innerJoin("SVC_CONTRATANTE SC", "SC.ID_CONTRATANTE  = SPSFPA.ID_TITULAR")
				.innerJoin("SVC_PERSONA SP", "SP.ID_PERSONA = SC.ID_PERSONA")
				.innerJoin("SVT_DOMICILIO SD", "SD.ID_DOMICILIO  = SC.ID_DOMICILIO")
				.leftJoin("SVT_TITULAR_BENEFICIARIOS STB2",
						"STB2.ID_TITULAR_BENEFICIARIOS  = SPSFPA.ID_TITULAR_SUBSTITUTO")
				.leftJoin("SVC_PERSONA SP2", "SP2.ID_PERSONA = STB2.ID_PERSONA")
				.leftJoin("SVT_DOMICILIO SD2", "SD2.ID_DOMICILIO  = STB2.ID_DOMICILIO")
				.leftJoin("SVT_TITULAR_BENEFICIARIOS STB3", "STB3.ID_TITULAR_BENEFICIARIOS  = SPSFPA.ID_BENEFICIARIO_1")
				.leftJoin("SVC_PERSONA SP3", "SP3.ID_PERSONA = STB3.ID_PERSONA")
				.leftJoin("SVT_DOMICILIO SD3", "SD3.ID_DOMICILIO  = STB3.ID_DOMICILIO")
				.leftJoin("SVT_TITULAR_BENEFICIARIOS STB4", "STB4.ID_TITULAR_BENEFICIARIOS  = SPSFPA.ID_BENEFICIARIO_2")
				.leftJoin("SVC_PERSONA SP4", "SP4.ID_PERSONA = STB4.ID_PERSONA")
				.leftJoin("SVT_DOMICILIO SD4", "SD4.ID_DOMICILIO  = STB4.ID_DOMICILIO")

				.leftJoin("SVC_PAIS p", "p.ID_PAIS= SP.ID_PAIS")
				.leftJoin("SVC_PAIS p2", "p2.ID_PAIS= SP2.ID_PAIS")
				.leftJoin("SVC_PAIS p3", "p3.ID_PAIS= SP3.ID_PAIS")
				.leftJoin("SVC_PAIS p4", "p4.ID_PAIS= SP4.ID_PAIS")
				.leftJoin("SVC_ESTADO e", "e.ID_ESTADO = SP.ID_ESTADO")
				.leftJoin("SVC_ESTADO e2", "e2.ID_ESTADO = SP2.ID_ESTADO")
				.leftJoin("SVC_ESTADO e3", "e3.ID_ESTADO = SP3.ID_ESTADO")
				.leftJoin("SVC_ESTADO e4", "e4.ID_ESTADO = SP4.ID_ESTADO")

				.innerJoin("SVC_VELATORIO SV", "SV.ID_VELATORIO = SPSFPA.ID_VELATORIO")
				.where("SPSFPA.ID_PLAN_SFPA = :idPlanSfpa").setParameter("idPlanSfpa", idPlanSfpa)
				.and("SPSFPA.IND_ACTIVO = 1");
		final String query = queryUtil.build();
		log.info(" consultaDetallePlanSfpa: " + query);
		log.info(" TERMINO - consultaDetallePlanSfpa");
		return query;
	}

	public String consultaLineaDetallePlanSFPA(String cveUsuario) {
		log.info(" INICIO - consultaLineaDetallePlanSFPA");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SPSFPA.ID_PLAN_SFPA as idPlanSfpa",
				"IFNULL(SPSFPA.NUM_FOLIO_PLAN_SFPA, '') as numFolioPlanSfpa", "IFNULL(SP.CVE_CURP, '') as curpTitular",
				"IFNULL(CONCAT_WS(' ',SP.NOM_PERSONA,SP.NOM_PRIMER_APELLIDO,SP.NOM_SEGUNDO_APELLIDO), '') AS nombreTitular",
				"IFNULL(SP2.CVE_CURP, '') as curpSubtitular",
				"IFNULL(CONCAT_WS(' ',SP2.NOM_PERSONA,SP2.NOM_PRIMER_APELLIDO,SP2.NOM_SEGUNDO_APELLIDO), '') AS nombreSubtitular")
				.from("SVT_PLAN_SFPA SPSFPA").innerJoin("SVC_CONTRATANTE SC", "SC.ID_CONTRATANTE = SPSFPA.ID_TITULAR")
				.innerJoin("SVC_PERSONA SP", "SP.ID_PERSONA = SC.ID_PERSONA")
				.innerJoin("SVT_USUARIOS SU", "SU.ID_PERSONA = SP.ID_PERSONA")
				.innerJoin("SVT_DOMICILIO SD", "SD.ID_DOMICILIO = SC.ID_DOMICILIO")
				.leftJoin("SVT_TITULAR_BENEFICIARIOS STB2",
						"STB2.ID_TITULAR_BENEFICIARIOS = SPSFPA.ID_TITULAR_SUBSTITUTO")
				.leftJoin("SVC_PERSONA SP2", "SP2.ID_PERSONA = STB2.ID_PERSONA")
				.leftJoin("SVT_DOMICILIO SD2", "SD2.ID_DOMICILIO = STB2.ID_DOMICILIO")
				.innerJoin("SVC_VELATORIO SV", "SV.ID_VELATORIO = SPSFPA.ID_VELATORIO")
				.where("SU.CVE_USUARIO = :cveUsuario").setParameter("cveUsuario", cveUsuario);
		final String query = queryUtil.build();
		log.info(" TERMINO - consultaLineaDetallePlanSFPA" + query);
		return query;
	}

}
