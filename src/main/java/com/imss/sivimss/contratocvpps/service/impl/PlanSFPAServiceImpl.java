package com.imss.sivimss.contratocvpps.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.imss.sivimss.contratocvpps.beans.DetallePlanSfpa;
import com.imss.sivimss.contratocvpps.beans.FolioOrdenServicio;
import com.imss.sivimss.contratocvpps.beans.InsertaActualizaPlanSfpa;
import com.imss.sivimss.contratocvpps.beans.PlanSFPA;
import com.imss.sivimss.contratocvpps.exception.BadRequestException;
import com.imss.sivimss.contratocvpps.model.request.ContratanteRequest;
import com.imss.sivimss.contratocvpps.model.request.FolioRequest;
import com.imss.sivimss.contratocvpps.model.request.InsertPlanSfpaRequest;
import com.imss.sivimss.contratocvpps.model.request.PlanSFPARequest;
import com.imss.sivimss.contratocvpps.model.request.TitularRequest;
import com.imss.sivimss.contratocvpps.model.request.UsuarioDto;
import com.imss.sivimss.contratocvpps.model.response.FolioResponse;
import com.imss.sivimss.contratocvpps.model.response.PersonaResponse;
import com.imss.sivimss.contratocvpps.model.response.PlanSFPAResponse;
import com.imss.sivimss.contratocvpps.repository.ActualizarPlanSFPARepository;
import com.imss.sivimss.contratocvpps.repository.GuardarPlanSFPARepository;
import com.imss.sivimss.contratocvpps.repository.PlanSFPARepository;
import com.imss.sivimss.contratocvpps.service.ReportePagoAnticipadoService;
import com.imss.sivimss.contratocvpps.service.PlanSFPAService;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.ConsultaConstantes;
import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.LogUtil;
import com.imss.sivimss.contratocvpps.util.MensajeResponseUtil;
import com.imss.sivimss.contratocvpps.util.ProviderServiceRestTemplate;
import com.imss.sivimss.contratocvpps.util.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanSFPAServiceImpl implements PlanSFPAService {
	
	private static final String NO_SE_ENCONTRO_INFORMACION = "45"; // No se encontró información relacionada a tu
	private static final String ERROR_AL_EJECUTAR_EL_QUERY = "Error al ejecutar el query ";
	private static final String FALLO_AL_EJECUTAR_EL_QUERY = "Fallo al ejecutar el query: ";
	private static final String INFORMACION_INCOMPLETA = "Informacion incompleta";
	private static final String CURP_RFC_YA_INGRESADO = "13"; // La CURP/RFC que deseas ingresar ya se encuentra registrada en el sistema.
	private static final String ERROR_INFORMACION = "52"; // Error al consultar la información.
	private static final String CONSULTA_GENERICA = "/consulta";
	private static final String CURP_NO_VALIDO = "34"; // CURP no valido.
	private static final String RFC_NO_VALIDO = "33"; // R.F.C. no valido.
	private static final String CONSULTA = "consulta";

	@Value("${endpoints.mod-catalogos}")
	private String urlModCatalogos;

	@Value("${formato_fecha}")
	private String formatoFecha;

	@Value("${endpoints.ms-reportes}")
	private String urlReportes;

	@Value("${endpoints.ms-rfc}")
	private String urlRfc;

	@Value("${endpoints.ms-curp}")
	private String urlCurp;

	@Autowired
	private LogUtil logUtil;

	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	@Autowired
	private PlanSFPARepository planSFPARepository;
	
	@Autowired
	private GuardarPlanSFPARepository guardarPlanSFPARepository;
	
	@Autowired
	private ActualizarPlanSFPARepository actualizarPlanSFPARepository;
	
	private Response<Object>response;
	
	@Autowired
	private ReportePagoAnticipadoService reportePagoAnticipadoService;

	@Override
	public Response<Object> detalleContratanteRfc(DatosRequest request, Authentication authentication)
			throws IOException {
		String consulta = "";
		try {
			
			TitularRequest titularRequest = new Gson().fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), TitularRequest.class);
			
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), " detalle contratante rfc ", CONSULTA, authentication);

			if (titularRequest.getRfc() == null) {
				throw new BadRequestException(HttpStatus.BAD_REQUEST, INFORMACION_INCOMPLETA);
			}
			
			response = providerRestTemplate.consumirServicioObject(new PlanSFPA().detalleContratanteRfc(request, titularRequest).getDatos(),urlModCatalogos.concat(CONSULTA_GENERICA), authentication);
			consulta = new PlanSFPA().detalleContratanteRfc(request, titularRequest).getDatos().get(AppConstantes.QUERY).toString();
			if (response.getCodigo()==200 && !response.getDatos().toString().contains("[]")) {
				response.setMensaje("interno");
				return response;
			} else if (response.getCodigo() == 200 && response.getDatos().toString().contains("[]")) {
				response = providerRestTemplate.consumirServicioObject(urlRfc.concat("/").concat(titularRequest.getRfc()), 0);
				if (response.getCodigo() == 200 && response.getDatos().toString().toLowerCase().contains("ACTIVO".toLowerCase())) {
					response.setMensaje("externo");
					return response;
				} else {
					return MensajeResponseUtil.mensajeConsultaResponseObject(response, RFC_NO_VALIDO);
				}
			}
			return MensajeResponseUtil.mensajeConsultaResponseObject(response, "5");
		} catch (Exception e) {
			String decoded = new String(DatatypeConverter.parseBase64Binary(consulta));
			log.error(ERROR_AL_EJECUTAR_EL_QUERY + decoded);
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), FALLO_AL_EJECUTAR_EL_QUERY + decoded, CONSULTA,
					authentication);
			throw new IOException(ERROR_INFORMACION, e.getCause());
		}
	}

	@Override
	public Response<Object> detalleContratanteCurp(DatosRequest request, Authentication authentication)
			throws IOException {
		String consulta = "";
		try {
			TitularRequest titularRequest = new Gson().fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), TitularRequest.class);
			
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(), " detalle contratante curp ", CONSULTA, authentication);

			if (titularRequest.getCurp() == null) {
				throw new BadRequestException(HttpStatus.BAD_REQUEST, INFORMACION_INCOMPLETA);
			}

			response = providerRestTemplate.consumirServicioObject(new PlanSFPA().detalleContratanteCurp(request, titularRequest).getDatos(),urlModCatalogos.concat(CONSULTA_GENERICA), authentication);
			consulta = new PlanSFPA().detalleContratanteCurp(request, titularRequest).getDatos().get(AppConstantes.QUERY).toString();
			if (response.getCodigo() == 200 && !response.getDatos().toString().contains("[]")) {
				response.setMensaje("interno");
				return response;
			} else if (response.getCodigo() == 200 && response.getDatos().toString().contains("[]")) {
				response = providerRestTemplate.consumirServicioObject(urlCurp.concat("/").concat(titularRequest.getCurp()), 1);
				if (response.getCodigo() == 200 && !response.getDatos().toString().toLowerCase().contains("LA CURP NO SE ENCUENTRA EN LA BASE DE DATOS".toLowerCase())) {
					response.setMensaje("externo");
					return response;
				} else {
					return MensajeResponseUtil.mensajeConsultaResponseObject(response, CURP_NO_VALIDO);
				}
			}
			return MensajeResponseUtil.mensajeConsultaResponseObject(response, "5");
		} catch (Exception e) {
			String decoded = new String(DatatypeConverter.parseBase64Binary(consulta));
			log.error(ERROR_AL_EJECUTAR_EL_QUERY + decoded);
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), FALLO_AL_EJECUTAR_EL_QUERY + decoded, CONSULTA,
					authentication);
			throw new IOException(ERROR_INFORMACION, e.getCause());
		}
	}

	@Override
	public Response<Object> consultaTipoContratacion(DatosRequest request, Authentication authentication)
			throws IOException {
		try {
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), " consulta tipo contratacion ", CONSULTA, authentication);

			return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioObject(new PlanSFPA().consultaTipoContratacion(request).getDatos(),
					urlModCatalogos.concat(CONSULTA_GENERICA), authentication),NO_SE_ENCONTRO_INFORMACION);

		} catch (Exception e) {
			e.printStackTrace();
			String consulta = new PlanSFPA().consultaTipoContratacion(request).getDatos().get(AppConstantes.QUERY).toString();
			String decoded = new String(DatatypeConverter.parseBase64Binary(consulta));
			log.error(ERROR_AL_EJECUTAR_EL_QUERY + decoded);
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), FALLO_AL_EJECUTAR_EL_QUERY + decoded, CONSULTA,
					authentication);
			throw new IOException(ERROR_INFORMACION, e.getCause());
		}
	}

	@Override
	public Response<Object> consultaTipoPagoMensual(DatosRequest request, Authentication authentication)
			throws IOException {
		try {
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), " consulta tipo contratacion ", CONSULTA, authentication);

			return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioObject(new PlanSFPA().consultaTipoPagoMensual(request).getDatos(),
					urlModCatalogos.concat(CONSULTA_GENERICA), authentication),NO_SE_ENCONTRO_INFORMACION);

		} catch (Exception e) {
			e.printStackTrace();
			String consulta = new PlanSFPA().consultaTipoPagoMensual(request).getDatos().get(AppConstantes.QUERY).toString();
			String decoded = new String(DatatypeConverter.parseBase64Binary(consulta));
			log.error(ERROR_AL_EJECUTAR_EL_QUERY + decoded);
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), FALLO_AL_EJECUTAR_EL_QUERY + decoded, CONSULTA,
					authentication);
			throw new IOException(ERROR_INFORMACION, e.getCause());
		}
	}

	@Override
	public Response<Object> consultaPromotores(DatosRequest request, Authentication authentication) throws IOException {
		try {
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), " consulta promotores ", CONSULTA, authentication);

			return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioObject(new PlanSFPA().consultaPromotores(request).getDatos(),
					urlModCatalogos.concat(CONSULTA_GENERICA), authentication),NO_SE_ENCONTRO_INFORMACION);

		} catch (Exception e) {
			e.printStackTrace();
			String consulta = new PlanSFPA().consultaPromotores(request).getDatos().get(AppConstantes.QUERY).toString();
			String decoded = new String(DatatypeConverter.parseBase64Binary(consulta));
			log.error(ERROR_AL_EJECUTAR_EL_QUERY + decoded);
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), FALLO_AL_EJECUTAR_EL_QUERY + decoded, CONSULTA,
					authentication);
			throw new IOException(ERROR_INFORMACION, e.getCause());
		}
	}

	@Override
	public Response<Object> consultaPaquetes(DatosRequest request, Authentication authentication) throws IOException {
		try {
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), " consulta paquetes ", CONSULTA, authentication);

			return MensajeResponseUtil.mensajeConsultaResponse(providerRestTemplate.consumirServicioObject(new PlanSFPA().consultaPaquetes(request).getDatos(),
					urlModCatalogos.concat(CONSULTA_GENERICA), authentication),NO_SE_ENCONTRO_INFORMACION);

		} catch (Exception e) {
			e.printStackTrace();
			String consulta = new PlanSFPA().consultaPaquetes(request).getDatos().get(AppConstantes.QUERY).toString();
			String decoded = new String(DatatypeConverter.parseBase64Binary(consulta));
			log.error(ERROR_AL_EJECUTAR_EL_QUERY + decoded);
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), FALLO_AL_EJECUTAR_EL_QUERY + decoded, CONSULTA,
					authentication);
			throw new IOException(ERROR_INFORMACION, e.getCause());
		}
	}
	
	@Override
	public Response<Object> consultaValidaAfiliado(DatosRequest request, Authentication authentication) throws IOException {
		String query = "";
		try {
			ContratanteRequest contratanteRequest = new Gson().fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), ContratanteRequest.class);
			
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), "consulta valida afiliado ", CONSULTA, authentication);
			
			query = new PlanSFPA().consultaValidaAfiliado(request, contratanteRequest).getDatos().get(AppConstantes.QUERY).toString();
			
			response =providerRestTemplate.consumirServicioObject(new PlanSFPA().consultaValidaAfiliado(request, contratanteRequest).getDatos(),
					urlModCatalogos.concat(CONSULTA_GENERICA), authentication);
			
			if (response.getCodigo()==200 && !response.getDatos().toString().contains("[]")) {
				return MensajeResponseUtil.mensajeResponse(response, CURP_RFC_YA_INGRESADO);
				
			} else if (response.getCodigo()==200 && response.getDatos().toString().contains("[]")) {
				return MensajeResponseUtil.mensajeConsultaResponse(response, NO_SE_ENCONTRO_INFORMACION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String decoded = new String(DatatypeConverter.parseBase64Binary(query));
			log.error(ERROR_AL_EJECUTAR_EL_QUERY + decoded);
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), FALLO_AL_EJECUTAR_EL_QUERY + decoded, CONSULTA,
					authentication);
			throw new IOException(ERROR_INFORMACION, e.getCause());
		}
		return response;
	}
	
	@Override
	public Response<Object> registrarPlanSFPA(DatosRequest request, Authentication authentication)throws IOException, SQLException {
		try {
			PlanSFPARequest planSFPARequest = new Gson().fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), PlanSFPARequest.class);
			UsuarioDto usuarioDto = new Gson().fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString()," insertar plan sfpa", AppConstantes.ALTA,authentication);
			List<ContratanteRequest> contratante = planSFPARequest.getTitularesBeneficiarios().stream().map(contratanteRequest ->  consultaExistePersona(request, authentication, contratanteRequest)) .collect(Collectors.toList());
			planSFPARequest.setTitularesBeneficiarios(contratante);
			planSFPARequest.setNumFolioPlanSfpa("(".concat(new PlanSFPA().obtenerFolioPlanSFPA(planSFPARequest, usuarioDto)));
			InsertPlanSfpaRequest insertPlanSfpaRequest = new InsertaActualizaPlanSfpa().insertaPlanSfpa(planSFPARequest, usuarioDto);
			PlanSFPAResponse planResponse =  guardarPlanSFPARepository.generaPlanSfpa(insertPlanSfpaRequest);
			if (planResponse.getIdPlanSfpa() != null) {
				Map<String, Object> map = new HashMap<>();
				Map<String, Object> datos = new HashMap<>();
				map.put("idPlanSFPA", planResponse.getIdPlanSfpa());
				DatosRequest datosRequest = new DatosRequest();
				datos.put(AppConstantes.DATOS, map);
				datosRequest.setDatos(datos);
				response = reportePagoAnticipadoService.generaReporteConvenioPagoAnticipado(datosRequest, authentication);
				if (response.getCodigo() == 200 && !response.getDatos().toString().contains("[]")){
					
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
		    logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_AL_GENERAR_FOLIO, AppConstantes.ALTA, authentication);
		    throw new IOException(AppConstantes.ERROR_GUARDAR, e.getCause());
		}
		return null;
	}



	private ContratanteRequest consultaExistePersona(DatosRequest request, Authentication authentication, ContratanteRequest contratanteRequest)  {
		PersonaResponse personaResponse;
		try {
			if(contratanteRequest.getPersona().equalsIgnoreCase(ConsultaConstantes.TITULAR)) {
				personaResponse = planSFPARepository.datoContratante(new PlanSFPA().consultaExisteContratante(contratanteRequest));
			} else {
				personaResponse = planSFPARepository.datoContratante(new PlanSFPA().consultaExisteTitularBeneficiarios(contratanteRequest));
			}
			
			if (personaResponse != null) {
				contratanteRequest.setIdContratante(personaResponse.getIdContratante());
				contratanteRequest.setIdTitularBeneficiarios(personaResponse.getIdTitularBeneficiarios());
				contratanteRequest.setIdPersona(personaResponse.getIdPersona());
				contratanteRequest.getCp().setIdDomicilio(personaResponse.getIdDomicilio());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return contratanteRequest;
	}

	@Override
	public Response<Object> cancelaPlanSFPA(DatosRequest request, Authentication authentication)
			throws IOException, SQLException {
		try {
			PlanSFPARequest planSFPARequest = new Gson().fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), PlanSFPARequest.class);
			UsuarioDto usuarioDto = new Gson().fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
			String insertPlanSfpaRequest = new InsertaActualizaPlanSfpa().updatePlanSfpa(planSFPARequest, usuarioDto);
			response =  planSFPARepository.cancelarPlanSfpa(insertPlanSfpaRequest);
		   } catch (Exception e) {
	        	e.printStackTrace();
				log.error(AppConstantes.ERROR_QUERY.concat(AppConstantes.ERROR_GUARDAR));
				log.error(e.getMessage());
			    logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_GUARDAR, AppConstantes.ALTA, authentication);
			    throw new IOException(AppConstantes.ERROR_GUARDAR, e.getCause());
			}
		return response;
	}

	@Override
	public Response<Object> consultaDetallePlanSfpa(DatosRequest request, Authentication authentication)
			throws IOException, SQLException {
		try {
			PlanSFPARequest planSFPARequest = new Gson().fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), PlanSFPARequest.class);
			String detalllePlanSfpa = new DetallePlanSfpa().consultaDetallePlanSfpa(planSFPARequest.getIdPlanSfpa());
			response =  planSFPARepository.consultarDetallePlanSfpa(detalllePlanSfpa);
		   } catch (Exception e) {
	        	e.printStackTrace();
				log.error(AppConstantes.ERROR_QUERY.concat(AppConstantes.ERROR_GUARDAR));
				log.error(e.getMessage());
			    logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_GUARDAR, AppConstantes.ALTA, authentication);
			    throw new IOException(AppConstantes.ERROR_GUARDAR, e.getCause());
			}
		return response;
	}
	
	@Override
	public Response<Object> consultarFolioOrden(DatosRequest request, Authentication authentication) throws IOException {
		String consulta = "";
		try {
            logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), "consultarFolioOrden", AppConstantes.CONSULTA, authentication);
            List<FolioResponse>folioResponses;
            FolioRequest folioRequest  = new Gson().fromJson(String.valueOf(request.getDatos().get(AppConstantes.DATOS)), FolioRequest.class);
			response = providerRestTemplate.consumirServicio(new FolioOrdenServicio().obtenerFolios(request, folioRequest).getDatos(), 
					urlModCatalogos.concat(AppConstantes.CATALOGO_CONSULTAR), authentication);
			consulta =new FolioOrdenServicio().obtenerFolios(request, folioRequest).getDatos().get(AppConstantes.QUERY).toString();
			if (response.getCodigo()==200 && !response.getDatos().toString().contains("[]")) {
				folioResponses= Arrays.asList(modelMapper.map(response.getDatos(), FolioResponse[].class));
				response.setDatos(folioResponses);
			}
			return MensajeResponseUtil.mensajeConsultaResponseObject(response, AppConstantes.ERROR_CONSULTAR);
		} catch (Exception e) {
	        String decoded = new String(DatatypeConverter.parseBase64Binary(consulta));
	        log.error(AppConstantes.ERROR_QUERY.concat(decoded));
	        log.error(e.getMessage());
	        logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), AppConstantes.ERROR_LOG_QUERY + decoded, AppConstantes.CONSULTA, authentication);
	        throw new IOException(AppConstantes.ERROR_CONSULTAR, e.getCause());
		}
	}



}