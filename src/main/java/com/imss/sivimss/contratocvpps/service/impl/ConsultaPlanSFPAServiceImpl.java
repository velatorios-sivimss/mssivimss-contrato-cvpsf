package com.imss.sivimss.contratocvpps.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.contratocvpps.beans.ConsultaPlanSFPA;
import com.imss.sivimss.contratocvpps.model.request.ReporteRequest;
import com.imss.sivimss.contratocvpps.service.ConsultaPlanSFPAService;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.LogUtil;
import com.imss.sivimss.contratocvpps.util.MensajeResponseUtil;
import com.imss.sivimss.contratocvpps.util.ProviderServiceRestTemplate;
import com.imss.sivimss.contratocvpps.util.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsultaPlanSFPAServiceImpl  implements ConsultaPlanSFPAService {
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Value("${endpoints.mod-catalogos}")
	private String urlModCatalogos;
	
	@Value("${endpoints.ms-reportes}")
	private String urlReportes;
	
	@Value("${reporte.consulta-plan-sfpa}")
	private String reporteConsultaPlanSFPA;
	
	@Autowired
	private LogUtil logUtil;
	
	Response<Object> response;
	
	private static final String ERROR_AL_DESCARGAR_DOCUMENTO = "64"; // Error en la descarga del documento.Intenta  nuevamente.
	private static final String NO_SE_ENCONTRO_INFORMACION = "45"; // No se encontró información relacionada a tu
	private static final String ERROR_AL_EJECUTAR_EL_QUERY = "Error al ejecutar el query ";
	private static final String FALLO_AL_EJECUTAR_EL_QUERY = "Fallo al ejecutar el query: ";
	private static final String ERROR_INFORMACION = "52"; // Error al consultar la información.
	private static final String GENERAR_DOCUMENTO = "Generar Reporte: " ;
	private static final String GENERA_DOCUMENTO = "Genera_Documento";
	private static final String CONSULTA_PAGINADO = "/paginado";
	private static final String CU68_NAME= "consulta plan SFPA : ";
	private static final String CONSULTA = "consulta";
	
	@Override
	public Response<Object> consultaPlanSFPA(DatosRequest request, Authentication authentication) throws IOException {
		String consulta = "";
		try {
			ReporteRequest reporteRequest= new Gson().fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), ReporteRequest.class);
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), " consulta plan SFPA ", CONSULTA, authentication);
			Map<String, Object> envioDatos = new ConsultaPlanSFPA().consultaPlanSFPA(request, reporteRequest).getDatos();
			consulta = queryDecoded(envioDatos);
			return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioObject(envioDatos,
					urlModCatalogos.concat(CONSULTA_PAGINADO), authentication),NO_SE_ENCONTRO_INFORMACION);
		} catch (Exception e) {
			e.printStackTrace();
			log.error( CU68_NAME + ERROR_AL_EJECUTAR_EL_QUERY + consulta);
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), FALLO_AL_EJECUTAR_EL_QUERY + consulta, CONSULTA,
					authentication);
			throw new IOException(ERROR_INFORMACION, e.getCause());
		}
	}
	
	private String queryDecoded (Map<String, Object> envioDatos ) {
		return new String(DatatypeConverter.parseBase64Binary(envioDatos.get(AppConstantes.QUERY).toString()));
	}

	@Override
	public Response<Object> generarReportePlanSFPA(DatosRequest request, Authentication authentication)
			throws IOException {
		String consulta = "";
		try {
			ReporteRequest reporteRequest= new Gson().fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), ReporteRequest.class);
			logUtil.crearArchivoLog(Level.INFO.toString(), CU68_NAME + GENERAR_DOCUMENTO + " Reporte Plan SFPA " + this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(), "generarReportePlanSFPA", GENERA_DOCUMENTO, authentication);
			Map<String, Object> envioDatos = new ConsultaPlanSFPA().generarReportePlanSFPA(request, reporteRequest,reporteConsultaPlanSFPA);
			consulta = envioDatos.get("condicion").toString();
			response = providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication);
			MensajeResponseUtil.mensajeResponseObject(response, ERROR_AL_DESCARGAR_DOCUMENTO);
		} catch (Exception e) {
			e.printStackTrace();
			log.error( CU68_NAME + ERROR_AL_EJECUTAR_EL_QUERY + consulta);
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), FALLO_AL_EJECUTAR_EL_QUERY + consulta, CONSULTA,
					authentication);
			throw new IOException(ERROR_INFORMACION, e.getCause());
		}
		return response;
	}


}
