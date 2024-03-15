package com.imss.sivimss.contratocvpps.beans;

import org.springframework.stereotype.Service;

import com.imss.sivimss.contratocvpps.model.request.ReporteDto;

@Service
public class ReportePagoAnticipado {

	public String generaReporte(ReporteDto reporteDto) {
		
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT PSFPA.IND_TITULAR_SUBSTITUTO indTitularSubs")
		.append(", PSFPA.NUM_FOLIO_PLAN_SFPA AS numFolioPlanSFPA")
		.append(",  DATE_FORMAT(P1.FEC_NAC, '%d-%m-%Y') fecNacTitular")
		.append(" , CONCAT(IFNULL(P1.NOM_PERSONA,''),' ',IFNULL(P1.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P1.NOM_SEGUNDO_APELLIDO,'')) AS nombreTitular ")
		.append("  , IF(SPIS.DES_PAIS = 119, 'Extranjera','Mexicana') AS nacionalidadTitular ")
		.append("  , P1.CVE_RFC AS rfcTitular ")
		.append(" , DO1.REF_CALLE AS calleTitular ")
		.append("  , DO1.NUM_EXTERIOR AS numExterior ")
		.append("  , DO1.NUM_INTERIOR AS numInterior ")
		.append(" , DO1.REF_COLONIA AS colonia ")
		.append("  , DO1.REF_CP AS codigoPostal")
		.append("  , DO1.REF_MUNICIPIO AS municipio ")
		.append("  , DO1.REF_ESTADO AS estado ")
		.append(" , P1.REF_CORREO AS correo ")
		.append(" , P1.REF_TELEFONO AS telefono ")
		.append(" , P1.REF_TELEFONO_FIJO AS telefonoFijo ")
		.append("  , PQ.MON_PRECIO AS totalImporte " )
		.append("  , TPM.DES_TIPO_PAGO_MENSUAL AS numPago ")
		.append("  , DOV.REF_ESTADO AS ciudadFirma ")
		.append("  , CONCAT(DATE_FORMAT(PSFPA.FEC_ALTA, '%d de '),ELT(MONTH(PSFPA.FEC_ALTA), \"ENERO\", \"FEBRERO\", \"MARZO\", \"ABRIL\", \"MAYO\", \"JUNIO\", \"JULIO\", \"AGOSTO\", \"SEPTIEMBRE\", \"OCTUBRE\", \"NOVIEMBRE\", \"DICIEMBRE\"),DATE_FORMAT(PSFPA.FEC_ALTA, ' de %Y')) AS fechaFirma")
		.append("  , PQ.REF_PAQUETE_NOMBRE AS nomPaquete ")
		.append("  ,(SELECT GROUP_CONCAT(CONCAT('$', IFNULL(FORMAT(PAGO.IMP_MONTO_MENSUAL,2) ,''),' - ',IFNULL(DATE_FORMAT(PAGO.FEC_PARCIALIDAD, '%d-%m-%Y'),'') ) SEPARATOR '\\n') FROM SVT_PAGO_SFPA PAGO JOIN SVT_PLAN_SFPA PLAN ON PLAN.ID_PLAN_SFPA = PAGO.ID_PLAN_SFPA WHERE PAGO.ID_PLAN_SFPA  =  " + reporteDto.getIdPlanSFPA() + " ) AS pagos ")
		.append(" , PQ.REF_PAQUETE_DESCRIPCION AS servInclPaquete ")
		.append("  , P5.REF_CORREO AS correoVelatorio ")
		.append("  , CTE.ID_CONTRATANTE AS numeroAfiliacion ")
		.append("  , CONCAT(IFNULL(P2.NOM_PERSONA,''),' ',IFNULL(P2.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P2.NOM_SEGUNDO_APELLIDO,'')) AS nombreSustituto ")
		.append(" , P2.FEC_NAC AS fecNacSustituto")
		.append("  , P2.CVE_RFC AS rfcSustituto ")
		.append("  , P2.REF_TELEFONO AS telefonoSustituto  ")
		.append("  , CONCAT(IFNULL(CONCAT(DO2.REF_CALLE,','),''),' ',IFNULL(CONCAT(DO2.NUM_EXTERIOR,','),''), ' ',IFNULL(CONCAT(DO2.NUM_INTERIOR,','),''),' ',IFNULL(CONCAT(DO2.REF_CP,','),''), ' ',IFNULL(CONCAT(DO2.REF_COLONIA,','),''),' ',IFNULL(CONCAT(DO2.REF_MUNICIPIO,','),''), ' ',IFNULL(CONCAT(DO2.REF_ESTADO,'.'),'')) AS direccionSustituto ")
		.append("  , CONCAT(IFNULL(P3.NOM_PERSONA,''),' ',IFNULL(P3.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P3.NOM_SEGUNDO_APELLIDO,'')) AS nombreB1 ")
		.append(" , DATE_FORMAT(P3.FEC_NAC, '%d-%m-%Y') AS fecNacB1 ")
		.append("  , P3.CVE_RFC AS rfcB1 ")
		.append("  , P3.REF_TELEFONO AS telefonoB1 ")
		.append("  , CONCAT(IFNULL(CONCAT(DO3.REF_CALLE,','),''),' ',IFNULL(CONCAT(DO3.NUM_EXTERIOR,','),''), ' ',IFNULL(CONCAT(DO3.NUM_INTERIOR,','),''),' ',IFNULL(CONCAT(DO3.REF_CP,','),''), ' ',IFNULL(CONCAT(DO3.REF_COLONIA,','),''),' ',IFNULL(CONCAT(DO3.REF_MUNICIPIO,','),''), ' ',IFNULL(CONCAT(DO3.REF_ESTADO,'.'),'')) AS direccionB1")
		.append("  , CONCAT(IFNULL(P4.NOM_PERSONA,''),' ',IFNULL(P4.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P4.NOM_SEGUNDO_APELLIDO,'')) AS nombreB2 ")
		.append("  , DATE_FORMAT(P4.FEC_NAC, '%d-%m-%Y') AS fecNacB2 ")
		.append("  , P4.CVE_RFC AS rfcB2 ")
		.append("  , P4.REF_TELEFONO AS telefonoB2 ")
		.append("  , CONCAT(IFNULL(CONCAT(DO4.REF_CALLE, ','),''),' ',IFNULL(CONCAT(DO4.NUM_EXTERIOR, ','),''), ' ',IFNULL(CONCAT(DO4.NUM_INTERIOR, ','),''), ' ',IFNULL(CONCAT(DO4.REF_CP, ','),''),' ',IFNULL(CONCAT(DO4.REF_COLONIA, ','),''),' ',IFNULL(CONCAT(DO4.REF_MUNICIPIO, ','),''), ' ',IFNULL(CONCAT(DO4.REF_ESTADO, '.'),'')) AS direccionB2 ")
		.append("  FROM SVT_PLAN_SFPA PSFPA  ")
		.append("  JOIN SVT_PAQUETE PQ ON PQ.ID_PAQUETE = PSFPA.ID_PAQUETE and PQ.IND_ACTIVO = 1 " )
		.append("  JOIN SVC_CONTRATANTE CTE ON CTE.ID_CONTRATANTE = PSFPA.ID_TITULAR ")
		.append(" JOIN SVC_PERSONA P1 ON P1.ID_PERSONA = CTE.ID_PERSONA ")
		.append("  LEFT JOIN SVC_PAIS SPIS ON P1.ID_PAIS = SPIS.ID_PAIS ")
		.append(" JOIN SVT_DOMICILIO DO1 ON CTE.ID_DOMICILIO = DO1.ID_DOMICILIO  ")
		.append(" JOIN SVC_TIPO_PAGO_MENSUAL TPM  on TPM.ID_TIPO_PAGO_MENSUAL = PSFPA.ID_TIPO_PAGO_MENSUAL  ")
		.append(" JOIN SVC_VELATORIO VO ON VO.ID_VELATORIO = PSFPA.ID_VELATORIO ")
		.append(" JOIN SVT_USUARIOS US ON US.ID_USUARIO = VO.ID_USUARIO_ADMIN ")
		.append(" LEFT JOIN  SVC_PERSONA P5 ON P5.ID_PERSONA  = US.ID_PERSONA ")
		.append(" JOIN SVT_DOMICILIO DO ON DO.ID_DOMICILIO = CTE.ID_DOMICILIO ")
		.append(" LEFT JOIN SVT_TITULAR_BENEFICIARIOS TBSP1 ON TBSP1.ID_TITULAR_BENEFICIARIOS  = PSFPA.ID_TITULAR_SUBSTITUTO ")
		.append(" LEFT JOIN SVC_PERSONA P2 ON P2.ID_PERSONA = TBSP1.ID_PERSONA ")
		.append(" LEFT JOIN SVT_DOMICILIO DO2 ON DO2.ID_DOMICILIO = TBSP1.ID_DOMICILIO ")
		.append(" LEFT JOIN SVT_TITULAR_BENEFICIARIOS TBSP2 ON TBSP2.ID_TITULAR_BENEFICIARIOS  = PSFPA.ID_BENEFICIARIO_1 ")
		.append(" LEFT JOIN SVC_PERSONA P3 ON P3.ID_PERSONA = TBSP2.ID_PERSONA ")
		.append(" LEFT JOIN SVT_DOMICILIO DO3 ON DO3.ID_DOMICILIO = TBSP2.ID_DOMICILIO ")
		.append(" LEFT JOIN SVT_TITULAR_BENEFICIARIOS TBSP3 ON TBSP3.ID_TITULAR_BENEFICIARIOS  = PSFPA.ID_BENEFICIARIO_2 ")
		.append(" LEFT JOIN SVC_PERSONA P4 ON P4.ID_PERSONA = TBSP3.ID_PERSONA ")
		.append(" LEFT JOIN SVT_DOMICILIO DO4 ON DO4.ID_DOMICILIO = TBSP3.ID_DOMICILIO")
		.append(" JOIN SVT_DOMICILIO DOV ON DOV.ID_DOMICILIO = VO.ID_DOMICILIO")
		.append(" WHERE PSFPA.ID_PLAN_SFPA = ")
		.append(reporteDto.getIdPlanSFPA());		
		return query.toString();
	}
	
	public String getImagenCheck() {
		return "SELECT TIP_PARAMETRO AS imgCheck FROM SVC_PARAMETRO_SISTEMA sps WHERE sps.ID_FUNCIONALIDAD = 25 AND sps.DES_PARAMETRO = 'IMAGEN_CHECK'";
	}
	
	public String getImagenFirma() {
		return " SELECT TIP_PARAMETRO AS firmDir FROM SVC_PARAMETRO_SISTEMA sps WHERE sps.ID_FUNCIONALIDAD = 25 AND sps.DES_PARAMETRO = 'FIRMA_DIRECTORA'";
	}
	
	public String getNomFibeso() {
		return "SELECT TIP_PARAMETRO AS nomFibeso FROM SVC_PARAMETRO_SISTEMA sps WHERE sps.ID_FUNCIONALIDAD = 20 AND sps.DES_PARAMETRO = 'NOMBRE FIBESO'";
	}
}
