package com.imss.sivimss.contratocvpps.beans;

import java.io.Serializable;

import com.imss.sivimss.contratocvpps.util.SelectQueryUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DetallePlanSfpa  implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String consultaDetallePlanSfpa(Integer idPlanSfpa) {
		log.info(" INICIO - consultaDetallePlanSfpa");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SPSFPA.ID_PLAN_SFPA as idPlanSfpa","SPSFPA.NUM_FOLIO_PLAN_SFPA as numFolioPlanSfpa","SPSFPA.ID_TIPO_CONTRATACION as idTipoContratacion",
		"SPSFPA.ID_PAQUETE as idPaquete","SPSFPA.ID_TIPO_PAGO_MENSUAL as idTipoPagoMensual","SPSFPA.IND_TITULAR_SUBSTITUTO as indTitularSubstituto",
		"SPSFPA.IND_MODIFICAR_TITULAR_SUBSTITUTO as indModificarTitularSubstituto","SP.CVE_RFC as rfc","SP.CVE_CURP as curp","SC.CVE_MATRICULA as matricula",
		"SP.CVE_NSS as nss","SP.NOM_PERSONA as nomPersona","SP.NOM_PRIMER_APELLIDO as primerApellido","SP.NOM_SEGUNDO_APELLIDO as segundoApellido",
		"SP.NUM_SEXO as sexo","SP.DES_OTRO_SEXO as otroSexo","SP.FEC_NAC as fecNacimiento","SP.ID_PAIS as idPais","SP.ID_ESTADO as idEstado",
		"SP.DES_TELEFONO as telefono","SP.DES_TELEFONO_FIJO as telefonoFijo","SP.DES_CORREO as correo","SP.TIPO_PERSONA as tipoPersona","SP.NUM_INE as ine",
		"SD.DES_CALLE as desCalle","SD.NUM_EXTERIOR as numExterior","SD.NUM_INTERIOR as numInterior","SD.DES_CP as codigoPostal","SD.DES_COLONIA as desColonia",
		"SD.DES_MUNICIPIO as desMunicipio","SD.DES_ESTADO as desEstado","SP2.CVE_RFC as rfc","SP2.CVE_CURP as curp","SC2.CVE_MATRICULA as matricula",
		"SP2.CVE_NSS as nss","SP2.NOM_PERSONA as nomPersona","SP2.NOM_PRIMER_APELLIDO as primerApellido","SP2.NOM_SEGUNDO_APELLIDO as segundoApellido",
		"SP2.NUM_SEXO as sexo","SP2.DES_OTRO_SEXO as otroSexo","SP2.FEC_NAC as fecNacimiento","SP2.ID_PAIS as idPais","SP2.ID_ESTADO as idEstado",
		"SP2.DES_TELEFONO as telefono","SP2.DES_TELEFONO_FIJO as telefonoFijo","SP2.DES_CORREO as correo","SP2.TIPO_PERSONA as tipoPersona","SP2.NUM_INE as ine",
		"SD2.DES_CALLE as desCalle","SD2.NUM_EXTERIOR as numExterior","SD2.NUM_INTERIOR as numInterior","SD2.DES_CP as codigoPostal","SD2.DES_COLONIA as desColonia",
		"SD2.DES_MUNICIPIO as desMunicipio","SD2.DES_ESTADO as desEstado"
		)
		.from("SVT_PLAN_SFPA SPSFPA").innerJoin("SVC_CONTRATANTE SC", "SC.ID_CONTRATANTE  = SPSFPA.ID_TITULAR")
		.innerJoin("SVC_PERSONA SP", "SP.ID_PERSONA = SC.ID_PERSONA").innerJoin("SVT_DOMICILIO SD", "SD.ID_DOMICILIO  = SC.ID_DOMICILIO")
		.innerJoin("SVC_CONTRATANTE SC2", "SC2.ID_CONTRATANTE  = SPSFPA.ID_TITULAR_SUBSTITUTO").innerJoin("SVC_PERSONA SP2", "SP2.ID_PERSONA = SC2.ID_PERSONA")
		.innerJoin("SVT_DOMICILIO SD2", "SD2.ID_DOMICILIO  = SC2.ID_DOMICILIO").where("SPSFPA.ID_PLAN_SFPA = :idPlanSfpa").setParameter("idPlanSfpa", idPlanSfpa)
		.and("SPSFPA.IND_ACTIVO = 1");
		final String query = queryUtil.build();
		log.info(" consultaDetallePlanSfpa: " + query);
		log.info(" TERMINO - consultaDetallePlanSfpa");
		return query;
	}

}
