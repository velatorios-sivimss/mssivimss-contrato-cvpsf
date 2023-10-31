package com.imss.sivimss.contratocvpps.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Level;

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
import com.imss.sivimss.contratocvpps.model.request.ReporteSiniestrosPFRequest;
import com.imss.sivimss.contratocvpps.model.response.ReportePagoAnticipadoReponse;
import com.imss.sivimss.contratocvpps.service.ReportePagoAnticipadoService;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.LogUtil;
import com.imss.sivimss.contratocvpps.util.MensajeResponseUtil;
import com.imss.sivimss.contratocvpps.util.NumeroAPalabra;
import com.imss.sivimss.contratocvpps.util.ProviderServiceRestTemplate;
import com.imss.sivimss.contratocvpps.util.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportePagoAnticipadoServiceImpl implements ReportePagoAnticipadoService {
	
	private static final String AGREGADO_CORRECTAMENTE= "30"; // Agregado correctamente.
	private static final String TIPO_REPORTE = "tipoReporte";
	private static final String RUTA_NOMBRE_REPORTE = "rutaNombreReporte";
	private static final String ERROR_AL_DESCARGAR_DOCUMENTO= "64"; // Error en la descarga del documento.Intenta nuevamente.
	private static final String CONSULTA = "consulta";

	@Autowired
	private ReportePagoAnticipado reportePagoAnticipado;
	
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
	
	@Autowired
	private LogUtil logUtil;
	
	@Value("${reporte.siniestros-prevision-funeraria}")
	private String generarReporteSiniestrosPF;
	
	
	@Override
	public Response<Object> generaReporteConvenioPagoAnticipado(DatosRequest request, Authentication authentication) throws IOException {
		Gson gson = new Gson();
		String datosJson = String.valueOf(request.getDatos().get(AppConstantes.DATOS));
		Map<String, Object>parametro= new HashMap<>();
		ReporteDto reporteDto= gson.fromJson(datosJson, ReporteDto.class);
		String query = reportePagoAnticipado.generaReporte(reporteDto);
		log.info(query );
		String encoded = DatatypeConverter.printBase64Binary(query.getBytes());
		parametro.put(AppConstantes.QUERY, encoded);
		request.setDatos(parametro);
		Response<Object> response = providerRestTemplate.consumirServicio(request.getDatos(),urlModCatalogos.concat(AppConstantes.CATALOGO_CONSULTAR), authentication);
		List<ReportePagoAnticipadoReponse> reportePagoAnticipadoReponse = Arrays.asList(modelMapper.map(response.getDatos(), ReportePagoAnticipadoReponse[].class));
		
		query = reportePagoAnticipado.getImagenFirma();
		log.info(query );
		encoded = DatatypeConverter.printBase64Binary(query.getBytes());
		parametro.put(AppConstantes.QUERY, encoded);
		request.setDatos(parametro);
		response = providerRestTemplate.consumirServicio(request.getDatos(),urlModCatalogos.concat(AppConstantes.CATALOGO_CONSULTAR), authentication);
		
		List<ReportePagoAnticipadoReponse> reportePagoAnticipadoReponseFirma = Arrays.asList(modelMapper.map(response.getDatos(), ReportePagoAnticipadoReponse[].class));
		query = reportePagoAnticipado.getImagenCheck();
		log.info(query );
		encoded = DatatypeConverter.printBase64Binary(query.getBytes());
		parametro.put(AppConstantes.QUERY, encoded);
		request.setDatos(parametro);
		response = providerRestTemplate.consumirServicio(request.getDatos(),urlModCatalogos.concat(AppConstantes.CATALOGO_CONSULTAR), authentication);
		List<ReportePagoAnticipadoReponse> reportePagoAnticipadoReponseCheck = Arrays.asList(modelMapper.map(response.getDatos(), ReportePagoAnticipadoReponse[].class));
		
    	Map<String, Object> envioDatos = generarMap(reportePagoAnticipadoReponse, reportePagoAnticipadoReponseFirma, reportePagoAnticipadoReponseCheck);
		envioDatos.put(TIPO_REPORTE, "pdf");
		envioDatos.put(RUTA_NOMBRE_REPORTE, convenioPagoAnticipado);
    	
		return MensajeResponseUtil.mensajeConsultaResponseObject(providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication), AGREGADO_CORRECTAMENTE);
		
	}
	private Map<String, Object> generarMap( List<ReportePagoAnticipadoReponse> contratoServicioInmediatoResponse
			, List<ReportePagoAnticipadoReponse> reportePagoAnticipadoReponseFirma
	        , List<ReportePagoAnticipadoReponse> reportePagoAnticipadoReponseCheck ) {
		Map<String, Object> envioDatos = new HashMap<>();
		envioDatos.put("ciudadFirma", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getCiudadFirma()));
		envioDatos.put("totalImporte",validarSiEsNull(contratoServicioInmediatoResponse.get(0).getTotalImporte()));
		envioDatos.put("paqueteAmparado", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getNomPaquete()));
		envioDatos.put("servInclPaquete", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getServInclPaquete()));
		envioDatos.put("nombreTitular", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getNombreTitular()));
		envioDatos.put("numPago", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getNumPago()));
		envioDatos.put("rfcTitular", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getRfcTitular()));
		envioDatos.put("fechaCPA", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getFechaFirma()));
		envioDatos.put("correoVelatorio", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getCorreoVelatorio()));
		envioDatos.put("cuotaAfiliacion", validarSiEsNull(contratoServicioInmediatoResponse.get(0).getCuotaAfiliacion()));
		envioDatos.put("numeroAfiliacion",validarSiEsNull(contratoServicioInmediatoResponse.get(0).getNumeroAfiliacion()));
		envioDatos.put("datosBancarios","");
		envioDatos.put("numeroINE","");
		envioDatos.put("totalLetra",NumeroAPalabra.convertirAPalabras(contratoServicioInmediatoResponse.get(0).getTotalImporte(), true));
		envioDatos.put("firmaDir",validarSiEsNull(reportePagoAnticipadoReponseFirma.get(0).getFirmDir()));
		envioDatos.put("imgCheck",validarSiEsNull(reportePagoAnticipadoReponseCheck.get(0).getImgCheck()));
	return envioDatos;
	}

	public static String validarSiEsNull(String valor) {
		if (valor != null) {
			return valor;
		}
		return "";
	}
	
	@Override
	public Response<Object> generarReporteSiniestros(DatosRequest request, Authentication authentication)
			throws IOException {
		try {
			ReporteSiniestrosPFRequest reporteSiniestrosRequest = new Gson().fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), ReporteSiniestrosPFRequest.class);
			Map<String, Object> envioDatos = generaReporteSiniestrosPF(reporteSiniestrosRequest, condicionconsultaSiniestrosPF(reporteSiniestrosRequest));
		   return MensajeResponseUtil.mensajeResponseObject(providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication), ERROR_AL_DESCARGAR_DOCUMENTO);
		} catch (Exception e) {
			log.error("Error.. {}", e.getMessage());
            logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), "Fallo al ejecutar el reporte : " + e.getMessage(), CONSULTA, authentication);
            throw new IOException("64", e.getCause());
		}
	}
	
	public String condicionconsultaSiniestrosPF(ReporteSiniestrosPFRequest reporteSiniestrosRequest) {
		StringBuilder condiciones =new StringBuilder();
		
		if(reporteSiniestrosRequest.getId_delegacion() != null) {
			condiciones.append(" AND sd.ID_DELEGACION = ").append(reporteSiniestrosRequest.getId_delegacion());
		}
		
		if(reporteSiniestrosRequest.getId_velatorio()!= null) {
			condiciones.append(" AND sv.ID_VELATORIO  = ").append(reporteSiniestrosRequest.getId_velatorio());
		}
		
		if (reporteSiniestrosRequest.getOds() != null) {
			condiciones.append(" AND sos.CVE_FOLIO = '").append(reporteSiniestrosRequest.getOds()).append("'");
		 }
		 
		if(reporteSiniestrosRequest.getFecha_inicial() != null && reporteSiniestrosRequest.getFecha_final() != null) {
			condiciones.append(" AND sos.FEC_ALTA  BETWEEN '").append(reporteSiniestrosRequest.getFecha_inicial()).append("' AND '").append(reporteSiniestrosRequest.getFecha_final()).append("'");
		}
		return condiciones.toString();
	}
	
	private Map<String, Object> generaReporteSiniestrosPF(ReporteSiniestrosPFRequest reporteSiniestrosRequest, String condiciones) {
		log.info(" INICIO - generaReporteSiniestrosPF : ");
		 
		Map<String, Object> envioDatos = new HashMap<>();
		envioDatos.put("condicion", condiciones);
		envioDatos.put("periodo", validarSiNulPeriodol(reporteSiniestrosRequest.getFecha_inicial(), reporteSiniestrosRequest.getFecha_final()));
		envioDatos.put("velatorio", validarSiEsNull(reporteSiniestrosRequest.getDes_velatorio()));
		envioDatos.put(TIPO_REPORTE, reporteSiniestrosRequest.getTipoReporte());
		envioDatos.put(RUTA_NOMBRE_REPORTE, generarReporteSiniestrosPF);
		if(reporteSiniestrosRequest.getTipoReporte().equals("xls")) {
			envioDatos.put("IS_IGNORE_PAGINATION", true);
		}
		
		log.info(" TERMINO - generaReporteSiniestrosPF");
		return envioDatos;
	}
	
	public static String validarSiNulPeriodol(String fechaInicial,  String fechaFinal) {
		if (fechaInicial != null && fechaFinal != null  ) {
			return LocalDate.parse( fechaInicial).format(DateTimeFormatter.ofPattern( "dd-MM-uuuu" )).toString().concat(" - ").concat(LocalDate.parse( fechaFinal).format(DateTimeFormatter.ofPattern( "dd-MM-uuuu" )).toString());
		}
		return "";
	}
}
