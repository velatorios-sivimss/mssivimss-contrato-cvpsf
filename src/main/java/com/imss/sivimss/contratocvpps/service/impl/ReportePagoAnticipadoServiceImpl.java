package com.imss.sivimss.contratocvpps.service.impl;

import java.io.IOException;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.contratocvpps.beans.ReportePagoAnticipado;
import com.imss.sivimss.contratocvpps.model.request.ReporteDto;
import com.imss.sivimss.contratocvpps.model.response.ReportePagoAnticipadoReponse;
import com.imss.sivimss.contratocvpps.service.ReportePagoAnticipadoService;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.MensajeResponseUtil;
import com.imss.sivimss.contratocvpps.util.NumeroAPalabra;
import com.imss.sivimss.contratocvpps.util.ProviderServiceRestTemplate;
import com.imss.sivimss.contratocvpps.util.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportePagoAnticipadoServiceImpl implements ReportePagoAnticipadoService {
	
	private static final String ERROR_AL_DESCARGAR_DOCUMENTO= "64"; // Error en la descarga del documento.Intenta nuevamente.
	private static final String TIPO_REPORTE = "tipoReporte";
	private static final String RUTA_NOMBRE_REPORTE = "rutaNombreReporte";

	@Autowired
	private ReportePagoAnticipado reporteODSRepository;
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Value("${endpoints.mod-catalogos}")
	private String urlModCatalogos;
	
	@Value("${endpoints.ms-reportes}")
	private String urlReportes;
	
	@Value("${reporte.anexo-convenio-pago-anticipado}")
	private String convenioPagoAnticipado;

	@Autowired 
	private ModelMapper modelMapper;
	
	
	@Override
	public Response<Object> generaReporteConvenioPagoAnticipado(DatosRequest request, Authentication authentication) throws IOException {
		Gson gson = new Gson();
		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		Map<String, Object>parametro= new HashMap<>();
		ReporteDto reporteDto= gson.fromJson(datosJson, ReporteDto.class);
		String query = reporteODSRepository.generaReporteODSCU025(reporteDto);
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
		parametro.put(AppConstantes.QUERY, encoded);
		request.setDatos(parametro);
		//request.setDatos(parametro);providerRestTemplate.consumirServicio(request.getDatos(), urlDominio.concat(AppConstantes.CATALOGO_CONSULTAR)
		Response<Object> response = providerRestTemplate.consumirServicio(request.getDatos(),urlModCatalogos.concat(AppConstantes.CATALOGO_CONSULTAR), authentication);
		List<ReportePagoAnticipadoReponse> reportePagoAnticipadoReponse = Arrays.asList(modelMapper.map(response.getDatos(), ReportePagoAnticipadoReponse[].class));
		
    	Map<String, Object> envioDatos = generarMap(reportePagoAnticipadoReponse);
		envioDatos.put(TIPO_REPORTE, "pdf");
		envioDatos.put(RUTA_NOMBRE_REPORTE, convenioPagoAnticipado);
    	
		return MensajeResponseUtil.mensajeConsultaResponseObject(providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication), ERROR_AL_DESCARGAR_DOCUMENTO);
		/*
		envioDatos.put("query", query);
		envioDatos.put(TIPO_REPORTE, reporteDto.getTipoReporte());
		envioDatos.put(RUTA_NOMBRE_REPORTE, generaReporteODS);
		try {
			log.info(CU025_NOMBRE);
			log.info(query );
			logUtil.crearArchivoLog(Level.INFO.toString(), CU025_NOMBRE + GENERAR_DOCUMENTO + " Genera Reporte ODS " + this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(), "generarReporteODS", GENERA_DOCUMENTO, authentication);
			Response<Object> response = providerServiceRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication);
		return   MensajeResponseUtil.mensajeConsultaResponse(response, ERROR_NO_SE_ENCONTRO_INFORMACION);
		} catch (Exception e) {
			log.error( CU025_NOMBRE + GENERAR_DOCUMENTO);
			logUtil.crearArchivoLog(Level.WARNING.toString(), CU025_NOMBRE + GENERAR_DOCUMENTO + this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(),"", GENERA_DOCUMENTO,
					authentication);
			throw new IOException("52", e.getCause());
		}	
		*/
	}
	private Map<String, Object> generarMap( List<ReportePagoAnticipadoReponse> contratoServicioInmediatoResponse) {
		Map<String, Object> envioDatos = new HashMap<>();
		envioDatos.put("lugarFirma", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getLugarFirma()));
		envioDatos.put("canPagoNum",validarSiEsNull(contratoServicioInmediatoResponse.get(0).getCanPagoNum()));
		envioDatos.put("paqueteAmparo", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getPaqueteAmparo()));
		envioDatos.put("nombreAfiliado", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getNombreAfiliado()));
		envioDatos.put("numPagos", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getNumPagos()));
		envioDatos.put("rfc", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getRfc()));
		envioDatos.put("fechaFirma", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getFechaFirma()));
		envioDatos.put("cuotaAfiliacion", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getCuotaAfiliacion()));
		envioDatos.put("numeroAfiliacion",validarSiEsNull(contratoServicioInmediatoResponse.get(0).getNumeroAfiliacion()));
		envioDatos.put("canPagoPalabras",NumeroAPalabra.convertirAPalabras(contratoServicioInmediatoResponse.get(0).getCanPagoNum(), true));
		
	return envioDatos;
	}

	public static String validarSiEsNull(String valor) {
		if (valor != null) {
			return valor;
		}
		return "";
	}
}
