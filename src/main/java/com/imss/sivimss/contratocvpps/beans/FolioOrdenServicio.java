package com.imss.sivimss.contratocvpps.beans;

import java.nio.charset.StandardCharsets;

import javax.xml.bind.DatatypeConverter;

import com.imss.sivimss.contratocvpps.model.request.FolioRequest;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.SelectQueryUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FolioOrdenServicio {
	
	public DatosRequest obtenerFolios(DatosRequest request, FolioRequest folioRequest ) {
		log.info(" INICIO - obtenerFolios");
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("DISTINCT SOS.ID_ORDEN_SERVICIO AS idOrdenServicio", "SOS.CVE_FOLIO AS folioOrdenServicio")
		.from("SVC_ORDEN_SERVICIO SOS")
		.innerJoin("SVC_VELATORIO SV", "SOS.ID_VELATORIO = SV.ID_VELATORIO")
		.innerJoin("SVC_DELEGACION SD", "SV.ID_DELEGACION = SD.ID_DELEGACION")
		.where("IFNULL(SOS.ID_ORDEN_SERVICIO,0) > 0")
		.and("SV.ID_DELEGACION = :idDelegacion").setParameter("idDelegacion", folioRequest.getIdDelegacion());
		 if (folioRequest.getIdVelatorio() != null  && 0 < folioRequest.getIdVelatorio()) {
			 queryUtil.and("SOS.ID_VELATORIO = :idVelatorio").setParameter("idVelatorio", folioRequest.getIdVelatorio());
		 }
		 queryUtil.orderBy("SOS.ID_ORDEN_SERVICIO ASC");
		final String query = queryUtil.build();
		log.info(" obtenerFolios: " + query);
		request.getDatos().put(AppConstantes.QUERY, DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8)));
		log.info(" TERMINO - obtenerFolios");
		return request;
	}

}
