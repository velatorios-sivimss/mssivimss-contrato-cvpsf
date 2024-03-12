package com.imss.sivimss.contratocvpps.beans;

import org.springframework.stereotype.Service;

import com.imss.sivimss.contratocvpps.model.request.RequestFiltroPaginado;

@Service
public class BeanQuerys {

	public String busquedaPaginada(RequestFiltroPaginado paginado) {
		return "SELECT SPLSFPA.ID_PLAN_SFPA AS idPlanSfpa, " +
				" SPLSFPA.NUM_FOLIO_PLAN_SFPA AS numFolio, " +
				" CONCAT_WS(' ', SP.NOM_PERSONA, SP.NOM_PRIMER_APELLIDO, SP.NOM_SEGUNDO_APELLIDO) AS nombreCompleto, " +
				" d.DES_DELEGACION AS estado, " +
				" IFNULL(SP.REF_CORREO,'') AS correo, " +
				" SPE.REF_PAQUETE_NOMBRE AS paquete, " +
				" TPM.DES_TIPO_PAGO_MENSUAL AS noPago, " +
				" SEPLSFPA.DES_ESTATUS_PLAN_SFPA AS estatusPlan " +
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
				" WHERE IFNULL(SPLSFPA.ID_PLAN_SFPA, 0) > 0 ";

	}

}
