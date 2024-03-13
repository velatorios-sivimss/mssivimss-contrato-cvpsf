package com.imss.sivimss.contratocvpps.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.imss.sivimss.contratocvpps.beans.BeanQuerys;
import com.imss.sivimss.contratocvpps.configuration.MyBatisConfig;
import com.imss.sivimss.contratocvpps.configuration.Mapper.Consultas;
import com.imss.sivimss.contratocvpps.configuration.Mapper.PlanSFPAMapper;
import com.imss.sivimss.contratocvpps.model.request.PagosSFPA;
import com.imss.sivimss.contratocvpps.model.request.PlanRequest;
import com.imss.sivimss.contratocvpps.model.request.PlanSFPA;
import com.imss.sivimss.contratocvpps.model.request.RequestFiltroPaginado;
import com.imss.sivimss.contratocvpps.model.request.UsuarioDto;
import com.imss.sivimss.contratocvpps.model.response.PlanResponse;
import com.imss.sivimss.contratocvpps.repository.PlanSFPARepository;
import com.imss.sivimss.contratocvpps.service.NuevoPlanSFPAService;
import com.imss.sivimss.contratocvpps.service.ReportePagoAnticipadoService;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.LogUtil;
import com.imss.sivimss.contratocvpps.util.PaginadoUtil;
import com.imss.sivimss.contratocvpps.util.Paginator;
import com.imss.sivimss.contratocvpps.util.ProviderServiceRestTemplate;
import com.imss.sivimss.contratocvpps.util.Response;

@Service
public class NuevoPlanSFPAServiceImplements implements NuevoPlanSFPAService {

	@Autowired
	private LogUtil logUtil;

	@Autowired
	private MyBatisConfig myBatisConfig;

	@Autowired
	private PaginadoUtil paginadoUtil;

	private static final Logger log = LoggerFactory.getLogger(NuevoPlanSFPAServiceImplements.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;

	private final String ERROR = "error: {}";

	private Gson gson = new Gson();

	private UsuarioDto usuario;
	@Autowired
	private Paginator paginador;
	@Autowired
	private BeanQuerys queryBusquedas;

	private PlanSFPAMapper planSFPAMapper;

	private static final String ID_PLAN_SFPA = "idPlanSFPA";

	private Response<Object> response;

	@Value("${endpoints.ms-reportes}")
	private String urlReportes;

	@Autowired
	private ReportePagoAnticipadoService reportePagoAnticipadoService;

	@Autowired
	private PlanSFPARepository planSFPARepository;

	@Override
	public Response<Object> detallePlanSFPA(Integer idPlanSFPA, Authentication authentication) throws IOException {
		List<Map<String, Object>> resultDatosPersona = new ArrayList<>();
		List<Map<String, Object>> resultDatosBeneficios = new ArrayList<>();

		// PersonaEmpresaConvenioResponse convenioResponse = new
		// PersonaEmpresaConvenioResponse();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			Consultas consultas = session.getMapper(Consultas.class);
			// resultDatosPersona = consultas
			// .selectNativeQuery(miConvenio.consultarDatosGenealesPersonaEmpresa(idConvenio,
			// idContratante));

			// resultDatosBeneficios = consultas.selectNativeQuery(
			// miConvenio.consultarBeneficiariosPersonaEmpresaConvenio(idConvenio,
			// idContratante));

		} catch (Exception e) {
			log.info(ERROR, e.getCause().getMessage());

			logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(),
					AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
					authentication);
			return new Response<>(true, HttpStatus.INTERNAL_SERVER_ERROR.value(), AppConstantes.OCURRIO_ERROR_GENERICO,
					Arrays.asList());
		}
		// convenioResponse.setPersona(resultDatosPersona);
		// convenioResponse.setBeneficiarios(resultDatosBeneficios);
		return null;

		// return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO,
		// convenioResponse);
	}

	@Override
	public Response<Object> busquedaPlanSFPA(DatosRequest paginado, Authentication authentication)
			throws IOException {

		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {

			RequestFiltroPaginado request = gson.fromJson(String.valueOf(paginado.getDatos().get(AppConstantes.DATOS)),
					RequestFiltroPaginado.class);

			Integer pagina = Integer.parseInt(paginado.getDatos().get("pagina").toString());
			Integer tamanio = Integer.parseInt(paginado.getDatos().get("tamanio").toString());
			String query = queryBusquedas.busquedaPaginada(request);
	        log.info("query {}", query);
			String columna = " SPLSFPA.NUM_FOLIO_PLAN_SFPA";
			String ordenamiento = "asc";
			return paginador.paginarConsulta(query, pagina, tamanio, columna, ordenamiento);

		} catch (Exception e) {
			log.info(ERROR, e.getCause().getMessage());

			logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(),
					AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
					authentication);
			return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "52");
		}

	}

	@Override
	public Response<Object> insertarPlanSFPA(DatosRequest datos, Authentication authentication) throws IOException {

		usuario = gson.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		String datosJson = datos.getDatos().get(AppConstantes.DATOS).toString();
		PlanRequest planSFPA = gson.fromJson(datosJson, PlanRequest.class);
		try (SqlSession session = sqlSessionFactory.openSession()) {
			planSFPAMapper = session.getMapper(PlanSFPAMapper.class);
			PlanSFPA plan = planSFPA.getPlan();
			PlanSFPA contratante = planSFPA.getContratante();
			PlanSFPA titularSubstituto = planSFPA.getTitularSubstituto();
			PlanSFPA beneficiario1 = planSFPA.getBeneficiario1();
			PlanSFPA beneficiario2 = planSFPA.getBeneficiario2();
			// insertar en contratante

			Integer idPersonaCurp=null;
			Integer idPersonaRfc;
			
			// sino existe insertar en persona y despues en domicilio y contratante
			if (contratante.getIdPersona() == null || contratante.getIdPersona() <= 0) {
				idPersonaCurp=this.buscarCurpRfc(titularSubstituto.getCurp(),1,session);
				idPersonaRfc=this.buscarCurpRfc(titularSubstituto.getRfc(),2,session);
				if (idPersonaCurp!=null || idPersonaRfc!=null ) {
					if (idPersonaCurp!=null) {
						contratante.setIdPersona(idPersonaCurp);
						contratante.setIdUsuario(usuario.getIdUsuario());
						planSFPAMapper.updatePersona(contratante);
					} else {
						contratante.setIdPersona(idPersonaRfc);
						contratante.setIdUsuario(usuario.getIdUsuario());
						planSFPAMapper.updatePersona(contratante);
					}
					planSFPAMapper.agregarDomicilio(contratante);
					planSFPAMapper.updateContratante(contratante);
					plan.setIdTitular(contratante.getIdContratante());
				}
				else {
				
				planSFPAMapper.agregarPersona(contratante);
				planSFPAMapper.agregarDomicilio(contratante);
				planSFPAMapper.agregarContratante(contratante);
				plan.setIdTitular(contratante.getIdContratante());
				}

			} else {
				planSFPAMapper.updatePersona(contratante);
				planSFPAMapper.updateDomicilio(contratante);
				planSFPAMapper.updateContratante(contratante);
			}
			// si existe actualizar persona contratante y domicilio

			// titular substituto
			// si es el mismo solamente se agrega a 1 a columna IND_TITULAR_SUBSTITUTO, esto

			// indica que el titular substituto es el mismo que el titular
			// sino se agrega la informacion en persona en caso de que no exista, si existe
			// la persona se actualiza y se inserta en la tabla SVT_TITULAR_BENEFICIARIOS
			// con la referencia de persona como titular substituto y su domiclio

			if (plan.getIndTitularSubstituto() == 0) {
				
				if ((titularSubstituto.getIdPersona() == null || titularSubstituto.getIdPersona() <= 0)) {
					idPersonaCurp=this.buscarCurpRfc(titularSubstituto.getCurp(),1,session);
					idPersonaRfc=this.buscarCurpRfc(titularSubstituto.getRfc(),2,session);
					if (idPersonaCurp!=null || idPersonaRfc!=null ) {
						if (idPersonaCurp!=null) {
							titularSubstituto.setIdPersona(idPersonaCurp);
							titularSubstituto.setIdUsuario(usuario.getIdUsuario());
							planSFPAMapper.updatePersona(titularSubstituto);
						} else {
							titularSubstituto.setIdPersona(idPersonaRfc);
							titularSubstituto.setIdUsuario(usuario.getIdUsuario());
							planSFPAMapper.updatePersona(titularSubstituto);
						}
							planSFPAMapper.agregarDomicilio(titularSubstituto);
							planSFPAMapper.agregarTitulaBeneficiario(titularSubstituto);
							plan.setIdTitularSubstituto(titularSubstituto.getIdTitularBeneficiario());
					}else {
						planSFPAMapper.agregarPersona(titularSubstituto);
						planSFPAMapper.agregarDomicilio(titularSubstituto);
						planSFPAMapper.agregarTitulaBeneficiario(titularSubstituto);
						plan.setIdTitularSubstituto(titularSubstituto.getIdTitularBeneficiario());
					}
				} else {
					planSFPAMapper.updatePersona(titularSubstituto);
					planSFPAMapper.agregarDomicilio(titularSubstituto);
					planSFPAMapper.agregarTitulaBeneficiario(titularSubstituto);
					plan.setIdTitularSubstituto(titularSubstituto.getIdTitularBeneficiario());

				}

			}

			// beneficiario1 se inserta en la tabla persona SVT_TITULAR_BENEFICIARIOS con la
			// referencia de beneficiario 1 y su domicilio
			if (beneficiario1 != null) {
				if (beneficiario1.getIdPersona() == null || beneficiario1.getIdPersona() <= 0) {
					idPersonaCurp=this.buscarCurpRfc(beneficiario1.getCurp(),1,session);
					idPersonaRfc=this.buscarCurpRfc(beneficiario1.getRfc(),2,session);
					if (idPersonaCurp!=null || idPersonaRfc!=null ) {
						if (idPersonaCurp!=null) {
							beneficiario1.setIdPersona(idPersonaCurp);
							beneficiario1.setIdUsuario(usuario.getIdUsuario());
							planSFPAMapper.updatePersona(beneficiario1);
						} else {
							beneficiario1.setIdPersona(idPersonaRfc);
							beneficiario1.setIdUsuario(usuario.getIdUsuario());
							planSFPAMapper.updatePersona(beneficiario1);
						}
						planSFPAMapper.agregarDomicilio(beneficiario1);
						planSFPAMapper.agregarTitulaBeneficiario(beneficiario1);
						plan.setIdBeneficiario1(beneficiario1.getIdTitularBeneficiario());
					}else {
						planSFPAMapper.agregarPersona(beneficiario1);
						planSFPAMapper.agregarDomicilio(beneficiario1);
						planSFPAMapper.agregarTitulaBeneficiario(beneficiario1);
						plan.setIdBeneficiario1(beneficiario1.getIdTitularBeneficiario());
					}

				} else {
					planSFPAMapper.updatePersona(beneficiario1);
					planSFPAMapper.agregarDomicilio(beneficiario1);
					planSFPAMapper.agregarTitulaBeneficiario(beneficiario1);
					plan.setIdBeneficiario1(beneficiario1.getIdTitularBeneficiario());
				}
			}
			// beneficiario2 se inserta en la tabla persona SVT_TITULAR_BENEFICIARIOS con la
			// referencia de beneficiario 2 y su domicilio
			if (beneficiario2 != null) {
				if (beneficiario2.getIdPersona() == null || beneficiario2.getIdPersona() <= 0) {
					idPersonaCurp=this.buscarCurpRfc(beneficiario2.getCurp(),1,session);
					idPersonaRfc=this.buscarCurpRfc(beneficiario2.getRfc(),2,session);
					if (idPersonaCurp!=null || idPersonaRfc!=null ) {
						if (idPersonaCurp!=null) {
							beneficiario2.setIdPersona(idPersonaCurp);
							beneficiario2.setIdUsuario(usuario.getIdUsuario());
							planSFPAMapper.updatePersona(beneficiario2);
						} else {
							beneficiario2.setIdPersona(idPersonaRfc);
							beneficiario2.setIdUsuario(usuario.getIdUsuario());
							planSFPAMapper.updatePersona(beneficiario2);
						}
						planSFPAMapper.agregarDomicilio(beneficiario2);
						planSFPAMapper.agregarTitulaBeneficiario(beneficiario2);
						plan.setIdBeneficiario2(beneficiario2.getIdTitularBeneficiario());
					}else {
						planSFPAMapper.agregarPersona(beneficiario2);
						planSFPAMapper.agregarDomicilio(beneficiario2);
						planSFPAMapper.agregarTitulaBeneficiario(beneficiario2);
						plan.setIdBeneficiario2(beneficiario2.getIdTitularBeneficiario());
					}

				} else {
					planSFPAMapper.updatePersona(beneficiario2);
					planSFPAMapper.agregarDomicilio(beneficiario2);
					planSFPAMapper.agregarTitulaBeneficiario(beneficiario2);
					plan.setIdBeneficiario2(beneficiario2.getIdTitularBeneficiario());
				}
			}

			// plan sfpa
			plan.setIdVelatorio(usuario.getIdVelatorio());
			plan.setIdUsuario(usuario.getIdUsuario());
			planSFPAMapper.agregarContratoPFPA(plan);
			// parcialidades

			BigDecimal cantidadTotal = new BigDecimal(plan.getMonPrecio());
			int numeroParcialidades = plan.getPagoMensual();
			List<BigDecimal> parcialidades = generarParcialidades(cantidadTotal, numeroParcialidades);

			PlanSFPAMapper mapperQuery = session.getMapper(PlanSFPAMapper.class);
			for (int i = 0; i < parcialidades.size(); i++) {
				PagosSFPA datosPagoSFPA = new PagosSFPA();
				datosPagoSFPA.setNoMes(i);
				datosPagoSFPA.setMontoParcialidad(parcialidades.get(i).doubleValue());
				datosPagoSFPA.setIdPlanSfpa(plan.getIdPlanSfpa());
				datosPagoSFPA.setIdUsuario(usuario.getIdUsuario());
				datosPagoSFPA.setIdEstatusPago(i == 0 ? 8 : 7);
				mapperQuery.agregarParcialidades(datosPagoSFPA);
			}
			session.commit();
			return new Response<>(false, HttpStatus.OK.value(), "Exito", plan);

		} catch (Exception e) {
			log.info(ERROR, e.getCause().getMessage());

			logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(),
					AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
					authentication);
			return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "52");
		}
	}

	public static List<BigDecimal> generarParcialidades(BigDecimal cantidadTotal, int numeroParcialidades) {
		// Redondeamos hacia arriba para asegurarnos de que no se pierdan centavos
		BigDecimal parcialidad = cantidadTotal.divide(new BigDecimal(numeroParcialidades), 2, RoundingMode.UP);
		List<BigDecimal> parcialidades = new ArrayList<>();

		// Generar las primeras (numeroParcialidades - 1) parcialidades
		for (int i = 0; i < numeroParcialidades - 1; i++) {
			parcialidades.add(parcialidad);
		}

		// Calcular la ultima parcialidad sumando los centavos
		BigDecimal sumaParcialidadesAnteriores = parcialidad.multiply(new BigDecimal(numeroParcialidades - 1));
		BigDecimal centavosRestantes = cantidadTotal.subtract(sumaParcialidadesAnteriores);
		parcialidades.add(centavosRestantes);

		return parcialidades;
	}

	@Override
	public Response<Object> busquedaDetallePlanSFPA(DatosRequest request, Authentication authentication)
			throws IOException {
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		ObjectMapper mappe = new ObjectMapper();
		JsonNode datos = mappe.readTree(request.getDatos().get(AppConstantes.DATOS)
                 .toString());
        Integer idPlan = datos.get("idPlan").asInt();
        Object personaAsociada;
        String json;
        JsonNode datosJson;
		PlanResponse planResponse = new PlanResponse();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			planSFPAMapper = session.getMapper(PlanSFPAMapper.class);
			personaAsociada = planSFPAMapper.datosPlan(idPlan);
            json = new ObjectMapper().writeValueAsString(personaAsociada);
            datosJson = mappe.readTree(json);
            Integer indTitularSubstituto = datosJson.get("indTitularSubstituto").asInt();
            Map<String, Object> plan = null;
            Map<String, Object> contratante = null;
            Map<String, Object> titularSubstituto = null;
            Map<String, Object> beneficiario1 = null;
            Map<String, Object> beneficiario2 = null;
			
			plan=planSFPAMapper.datosPlan(idPlan);
            contratante=planSFPAMapper.datosContratante(idPlan);
			if (indTitularSubstituto==0) {
				titularSubstituto=planSFPAMapper.datosContratanteSustituto(idPlan);
			}
			
		
			beneficiario1=planSFPAMapper.datosBeneficiario1(idPlan);
			beneficiario2=planSFPAMapper.datosBeneficiario2(idPlan);

			
			
			planResponse.setPlan(plan);
			planResponse.setContratante(contratante!=null?contratante:null);
			planResponse.setTitularSubstituto(titularSubstituto!=null?titularSubstituto:null);
			planResponse.setBeneficiario1(beneficiario1!=null?beneficiario1:null);
			planResponse.setBeneficiario2(beneficiario2!=null?beneficiario2:null);

			
			
			return new Response<>(false, HttpStatus.OK.value(), "Exito",planResponse);

		} catch (Exception e) {
			log.info(ERROR, e.getCause().getMessage());

			logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(),
					AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
					authentication);
			return new Response<>(true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "52");
		}

	}

	@Override
	public Response<Object> actualizarPlanSFPA(DatosRequest planSFPA, Authentication authentication)
			throws IOException {
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			
			// actualizar contratante
			// actualizar tablas contratante, domicilio
			
			// subsituto
			//IND_TITULAR_SUBSTITUTO=0 validar
			// si el id persona es null y el ID_TITULAR_SUBSTITUTO = null insertar en las tablas de 
			// persona y SVT_TITULAR_BENEFICIARIOS y actualizar ID_TITULAR_SUBSTITUTO en el plan
			// sino actualizar la tabla persona, domicilio 

		
			session.commit();
			return null;

		} catch (Exception e) {
			log.info(ERROR, e.getCause().getMessage());

			logUtil.crearArchivoLog(Level.WARNING.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(),
					AppConstantes.ERROR_LOG_QUERY + AppConstantes.ERROR_CONSULTAR, AppConstantes.CONSULTA,
					authentication);
			return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "52");
		}
	}
	
	private Integer buscarCurpRfc(String datos, Integer busqueda,SqlSession session) throws JsonProcessingException {
		ObjectMapper mappe = new ObjectMapper();
		Object personaAsociada=null;
	    String json;
	    JsonNode jsonNode;
	    PlanSFPAMapper planSFPAMappr=session.getMapper(PlanSFPAMapper.class);
	    switch (busqueda) {
		case 1:
			personaAsociada = planSFPAMappr.buscaCurp(datos);
		      
			break;
		case 2:
			personaAsociada = planSFPAMappr.buscaRFC(datos);
			break;
		 default:
		  return null;
		}
	   
	    if (personaAsociada==null) {
			return null;
		} 
	    json = new ObjectMapper().writeValueAsString(personaAsociada);
		jsonNode = mappe.readTree(json);
		
		return jsonNode.get("idPersona").asInt();
      
        
	}

}
