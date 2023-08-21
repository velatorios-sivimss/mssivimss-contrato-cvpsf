package com.imss.sivimss.contratocvpps.beans;

import org.springframework.stereotype.Service;

import com.imss.sivimss.contratocvpps.model.request.ReporteDto;

@Service
public class ReportePagoAnticipado {

	public String generaReporte(ReporteDto reporteDto) {
		return "SELECT sp.MON_PRECIO as canPagoNum "
				+ ", sp.DES_NOM_PAQUETE as paqueteAmparo "
				+ ", CONCAT(sp2.NOM_PERSONA,' ',sp2.NOM_PRIMER_APELLIDO, ' ',sp2.NOM_SEGUNDO_APELLIDO) as nombreAfiliado "
				+ ", sp2.CVE_RFC as rfc "
				+ ", stpm.DES_TIPO_PAGO_MENSUAL as numPagos "
				+ ", sps.FEC_ALTA as fechaFirm "
				+ ", CONCAT(sv.DES_VELATORIO, ', ', se.DES_ESTADO  ) as lugarFirma "
				+ ", sp.MON_PRECIO as cuotaAfiliacion "
				+ ", (SELECT GROUP_CONCAT(ss.DES_SERVICIO SEPARATOR  ', ')"
				+ " FROM SVT_SERVICIO ss "
				+ " JOIN SVC_DETALLE_CARAC_PAQUETE sdcp on sdcp.ID_SERVICIO = ss.ID_SERVICIO "
				+ " JOIN SVC_CARACTERISTICAS_PAQUETE scp on scp.ID_CARAC_PAQUETE = sdcp.ID_CARACTERISTICAS_PAQUETE "
				+ " JOIN SVT_PLAN_SFPA sps on sps.ID_PAQUETE = scp.ID_PAQUETE "
				+ " WHERE sps.ID_PLAN_SFPA  = " + reporteDto.getIdPlanSFPA() + ") as servInclPaquete "
				+ ", sc.ID_CONTRATANTE as numeroAfiliacion "
				+ "FROM SVT_PLAN_SFPA sps  "
				+ "JOIN SVT_PAQUETE sp on sp.ID_PAQUETE = sps.ID_PAQUETE and sp.IND_ACTIVO = 1 "
				+ "JOIN SVC_CONTRATANTE sc on sc.ID_CONTRATANTE = sps.ID_TITULAR  "
				+ "JOIN SVC_PERSONA sp2 on sp2.ID_PERSONA = sc.ID_PERSONA  "
				+ "JOIN SVC_TIPO_PAGO_MENSUAL stpm  on stpm.ID_TIPO_PAGO_MENSUAL = sps.ID_TIPO_PAGO_MENSUAL  "
				+ "JOIN SVC_VELATORIO sv on sv.ID_VELATORIO = sps.ID_VELATORIO  "
				+ "JOIN SVC_ESTADO se on se.ID_ESTADO = sp2.ID_ESTADO  "
				+ "WHERE sps.ID_PLAN_SFPA = " + reporteDto.getIdPlanSFPA();
	}
	
	public String getImagenCheck() {
		return "SELECT TIP_PARAMETRO AS imgCheck FROM SVC_PARAMETRO_SISTEMA sps WHERE sps.ID_FUNCIONALIDAD = 25 AND sps.DES_PARAMETRO = 'IMAGEN_CHECK'";
	}
	
	public String getImagenFirma() {
		return "SELECT TIP_PARAMETRO AS firmDir FROM SVC_PARAMETRO_SISTEMA sps WHERE sps.ID_FUNCIONALIDAD = 25 AND sps.DES_PARAMETRO = 'FIRMA_DIRECTORA'";
	}
}
