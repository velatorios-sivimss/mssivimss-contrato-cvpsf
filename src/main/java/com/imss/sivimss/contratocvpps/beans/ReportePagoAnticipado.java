package com.imss.sivimss.contratocvpps.beans;

import org.springframework.stereotype.Service;

import com.imss.sivimss.contratocvpps.model.request.ReporteDto;

@Service
public class ReportePagoAnticipado {
	public String generaReporteODSCU025(ReporteDto reporteDto) {
		String query ="select sp.MON_PRECIO as canPagoNum "
				+ ", sp.DES_NOM_PAQUETE as paqueteAmparo "
				+ ", CONCAT(sp2.NOM_PERSONA,' ',sp2.NOM_PRIMER_APELLIDO, ' ',sp2.NOM_SEGUNDO_APELLIDO) as nombreAfiliado "
				+ ", sp2.CVE_RFC as rfc "
				+ ", stpm.DES_TIPO_PAGO_MENSUAL as numPagos "
				+ ", sps.FEC_ALTA as fechaFirm "
				+ ", CONCAT(sv.DES_VELATORIO, ', ', se.DES_ESTADO  ) as lugarFirma "
				+ ", sp.MON_PRECIO as cuotaAfiliacion "
				+ ", (select GROUP_CONCAT(ss.DES_NOM_SERVICIO SEPARATOR  ', ') from svt_servicio ss  "
				+ "join svc_detalle_caracteristicas_paquete sdcp on sdcp.ID_SERVICIO = ss.ID_SERVICIO  "
				+ "join svc_caracteristicas_paquete scp on scp.ID_CARACTERISTICAS_PAQUETE = sdcp.ID_CARACTERISTICAS_PAQUETE  "
				+ "join svc_orden_servicio sos on sos.ID_ORDEN_SERVICIO = scp.ID_ORDEN_SERVICIO  "
				+ "where sos.CVE_FOLIO = 'DOC-0001') as servInclPaquete "
				+ ", sc.ID_CONTRATANTE as numeroAfiliacion "
				+ "from svt_plan_sfpa sps  "
				+ "join svt_paquete sp on sp.ID_PAQUETE = sps.ID_PAQUETE and sp.IND_ACTIVO = 1 "
				+ "join svc_contratante sc on sc.ID_CONTRATANTE = sps.ID_TITULAR  "
				+ "join svc_persona sp2 on sp2.ID_PERSONA = sc.ID_PERSONA  "
				+ "join svc_tipo_pago_mensual stpm  on stpm.ID_TIPO_PAGO_MENSUAL = sps.ID_TIPO_PAGO_MENSUAL  "
				+ "join svc_velatorio sv on sv.ID_VELATORIO = sps.ID_VELATORIO  "
				+ "join svc_estado se on se.ID_ESTADO = sp2.ID_ESTADO  "
				+ "join svc_caracteristicas_paquete scp on scp.ID_PAQUETE = sp.ID_PAQUETE  "
				+ "join svc_orden_servicio sos on sos.ID_ORDEN_SERVICIO = scp.ID_ORDEN_SERVICIO  "
				+ "where sos.CVE_FOLIO = 'DOC-0001'";
	return query;	
	}
}
