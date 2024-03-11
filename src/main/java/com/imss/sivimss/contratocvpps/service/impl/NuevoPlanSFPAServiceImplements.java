package com.imss.sivimss.contratocvpps.service.impl;

import java.io.IOException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.imss.sivimss.contratocvpps.beans.BeanQuerys;
import com.imss.sivimss.contratocvpps.configuration.MyBatisConfig;
import com.imss.sivimss.contratocvpps.configuration.Mapper.Consultas;
import com.imss.sivimss.contratocvpps.model.request.PlanSFPA;
import com.imss.sivimss.contratocvpps.model.request.UsuarioDto;
import com.imss.sivimss.contratocvpps.service.NuevoPlanSFPAService;
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
	public Response<Object> busquedaPlanSFPA(Integer idPlanSFPA, Authentication authentication)
			throws IOException {

		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {

			String query = queryBusquedas.busquedaPaginada();
			int pagina = 1;
			int elementos = 10;
			String columna = " SPLSFPA.NUM_FOLIO_PLAN_SFPA";
			String ordenamiento = "asc";
			return paginador.paginarConsulta(query, pagina, elementos, columna, ordenamiento);

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
		Gson gson= new Gson();
		SqlSessionFactory sqlSessionFactory = myBatisConfig.buildqlSessionFactory();
		String datosJson=datos.getDatos().get(AppConstantes.DATOS).toString();
		PlanSFPA planSFPA= gson.fromJson(datosJson, PlanSFPA.class);
		try (SqlSession session = sqlSessionFactory.openSession()) {

			//insertar en contratante
			// sino existe insertar en persona y despues en domicilio y contratante
			// si existe actualizar persona contratante y domicilio
			
			
			// titular substituto
			// si es el mismo solamente se agrega a 1 a columna IND_TITULAR_SUBSTITUTO, esto indica que el titular substituto es el mismo que el titular 
			// sino se agrega la informacion en persona en caso de que no exista, si existe la persona se actualiza  y  se inserta en la tabla SVT_TITULAR_BENEFICIARIOS con la referencia de persona como titular substituto y su domiclio
			
			//beneficiario1 se inserta en la tabla persona SVT_TITULAR_BENEFICIARIOS con la referencia de beneficiario 1 y su domicilio
			
			
			//beneficiario2 se inserta en la tabla persona SVT_TITULAR_BENEFICIARIOS con la referencia de beneficiario 2 y su domicilio
			
			
			//plan sfpa
			
		
			//parcialidades
			
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

}
