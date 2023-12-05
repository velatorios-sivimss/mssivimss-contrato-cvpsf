package com.imss.sivimss.contratocvpps.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.imss.sivimss.contratocvpps.model.request.ReporteSiniestrosPFRequest;
import com.imss.sivimss.contratocvpps.service.impl.ReportePagoAnticipadoServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReporteConcentradoSFPA {
	
	public Map<String, Object> generaConcentradoSFPA(ReporteSiniestrosPFRequest concentradoRequest, String reporteConcentradoSFPA) throws ParseException {
		log.info(" INICIO - generaReporteSiniestrosPF : ");
		StringBuilder condicion = new StringBuilder();
		if(concentradoRequest.getId_delegacion()!=null) {
			condicion.append(" vel.ID_DELEGACION="+concentradoRequest.getId_delegacion());
		}
		if(concentradoRequest.getId_velatorio()!=null) {
			condicion.append(" sfpa.ID_VELATORIO="+concentradoRequest.getId_velatorio());
		}
		if(concentradoRequest.getFecha_inicial()!=null) {
			String fechaInicio = formatFecha(concentradoRequest.getFecha_inicial());
			condicion.append(" sfpa.FEC_INGRESO>="+fechaInicio);
		}
		if(concentradoRequest.getFecha_final()!=null) {
			String fechaFin = formatFecha(concentradoRequest.getFecha_final());
			condicion.append(" sfpa.FEC_INGRESO<="+fechaFin);
		}
		Map<String, Object> envioDatos = new HashMap<>();
		envioDatos.put("condition", condicion.toString());
		envioDatos.put("fecIncio", concentradoRequest.getFecha_inicial());
		envioDatos.put("fecFin", concentradoRequest.getFecha_final());
		envioDatos.put("tipoReporte", concentradoRequest.getTipoReporte());
		envioDatos.put("rutaNombreReporte", reporteConcentradoSFPA);
		if(concentradoRequest.getTipoReporte().equals("xls")) {
			envioDatos.put("IS_IGNORE_PAGINATION", true);
		}
		
		log.info(" TERMINO - generaReporteSiniestrosPF");
		return envioDatos;
	}

	private String formatFecha(String fecha) throws ParseException {
		Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
		DateFormat fecForma = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "MX"));
		return fecForma.format(dateF);     
	}

}
