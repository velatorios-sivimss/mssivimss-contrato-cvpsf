package com.imss.sivimss.contratocvpps.beans;

import org.springframework.stereotype.Service;

import com.imss.sivimss.contratocvpps.model.request.RequestFiltroPaginado;

@Service
public class BeanQuerys {

	public String busquedaPaginada(RequestFiltroPaginado datos) {

		String sql = "SELECT SPLSFPA.ID_PLAN_SFPA AS idPlanSfpa, " +
				" SPLSFPA.NUM_FOLIO_PLAN_SFPA AS numFolio, " +
				" CONCAT_WS(' ', SP.NOM_PERSONA, SP.NOM_PRIMER_APELLIDO, SP.NOM_SEGUNDO_APELLIDO) AS nombreCompleto, " +
				" d.DES_DELEGACION AS estado, " +
				" IFNULL(SP.REF_CORREO,'') AS correo, " +
				" SPE.REF_PAQUETE_NOMBRE AS paquete, " +
				" TPM.DES_TIPO_PAGO_MENSUAL AS noPago, " +
				" SEPLSFPA.DES_ESTATUS_PLAN_SFPA AS estatusPlan ," +
				" case when SEPLSFPA.ID_ESTATUS_PLAN_SFPA = 4 then 'PAGADO'" +
				" when SEPLSFPA.ID_ESTATUS_PLAN_SFPA = 6 then 'CANCELADO'" +
				" when pg.ID_ESTATUS_PAGO = 1 then 'VIGENTE'" +
				" ELSE  upper(dp.DES_ESTATUS_PAGO_ANTICIPADO) " +
				" END  AS estatusPago " +
				" FROM SVT_PLAN_SFPA SPLSFPA " +
				" JOIN SVC_CONTRATANTE SC ON SPLSFPA.ID_TITULAR = SC.ID_CONTRATANTE " +
				" JOIN SVC_PERSONA SP ON SP.ID_PERSONA = SC.ID_PERSONA " +
				" JOIN SVT_DOMICILIO SD ON SD.ID_DOMICILIO = SC.ID_DOMICILIO " +
				" JOIN SVT_PAQUETE SPE ON SPE.ID_PAQUETE = SPLSFPA.ID_PAQUETE " +
				" JOIN SVC_TIPO_PAGO_MENSUAL TPM ON TPM.ID_TIPO_PAGO_MENSUAL = SPLSFPA.ID_TIPO_PAGO_MENSUAL " +
				" JOIN SVC_VELATORIO SV ON SV.ID_VELATORIO = SPLSFPA.ID_VELATORIO " +
				" JOIN SVC_DELEGACION d ON d.ID_DELEGACION = SV.ID_DELEGACION " +
				" JOIN SVC_ESTATUS_PLAN_SFPA SEPLSFPA ON SEPLSFPA.ID_ESTATUS_PLAN_SFPA = SPLSFPA.ID_ESTATUS_PLAN_SFPA "
				+
				" LEFT JOIN SVT_PAGO_SFPA pg ON pg.ID_PLAN_SFPA = SPLSFPA.ID_PLAN_SFPA " +
				" AND  MONTH(pg.FEC_PARCIALIDAD) = MONTH(CURRENT_DATE())" +
				" AND  YEAR(pg.FEC_PARCIALIDAD) = YEAR(CURRENT_DATE())" +
				"  AND pg.IND_ACTIVO = 1" +
				" LEFT JOIN SVC_ESTATUS_PAGO_ANTICIPADO dp ON dp.ID_ESTATUS_PAGO_ANTICIPADO = pg.ID_ESTATUS_PAGO" +
				" WHERE IFNULL(SPLSFPA.ID_PLAN_SFPA, 0) > 0 ";

		if (datos.getIdVelatorio() != null) {
			sql += " AND SPLSFPA.ID_VELATORIO = " + datos.getIdVelatorio();
		}

		if (datos.getNumFolioPlanSfpa() != null) {
			sql += " AND SPLSFPA.NUM_FOLIO_PLAN_SFPA LIKE '%" + datos.getNumFolioPlanSfpa() + "%' ";
		}

		if (datos.getCurp() != null) {
			sql += " AND SP.CVE_CURP = '" + datos.getCurp() + "'";
		}

		if (datos.getRfc() != null) {
			sql += " AND SP.CVE_RFC = '" + datos.getCurp() + "'";
		}
		if (datos.getNombreAfiliado() != null) {
			sql += " AND CONCAT_WS(' ',SP.NOM_PERSONA,SP.NOM_PRIMER_APELLIDO,SP.NOM_SEGUNDO_APELLIDO ) LIKE '%"
					+ datos.getNombreAfiliado() + "%'";
		}

		if (datos.getIdEstatusPlanSfpa() != null) {
			sql += " AND SPLSFPA.ID_ESTATUS_PLAN_SFPA =" + datos.getIdEstatusPlanSfpa();
		}
		String fechas = "";

		if (datos.getFechaInicio() != null && datos.getFechaFin() != null) {
			fechas = " AND SPLSFPA.FEC_INGRESO  BETWEEN '" + datos.getFechaInicio() + "' AND '" + datos.getFechaFin()
					+ "'";
		} else if (datos.getFechaInicio() != null) {
			fechas = "  AND SPLSFPA.FEC_INGRESO >= '" + datos.getFechaInicio();
		} else if (datos.getFechaFin() != null) {
			fechas = "  AND SPLSFPA.FEC_INGRESO <= '" + datos.getFechaFin();
		}

		sql += fechas + " ";

		return sql;

	}

}
