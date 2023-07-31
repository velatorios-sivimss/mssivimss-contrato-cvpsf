package com.imss.sivimss.contratocvpps.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.imss.sivimss.contratocvpps.model.request.InsertPlanSfpaRequest;
import com.imss.sivimss.contratocvpps.model.request.ReporteRequest;
import com.imss.sivimss.contratocvpps.model.response.NumeroPagoPlanSfpaResponse;
import com.imss.sivimss.contratocvpps.model.response.PlanSFPAResponse;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.ConsultaConstantes;
import com.imss.sivimss.contratocvpps.util.ConvertirGenerico;
import com.imss.sivimss.contratocvpps.util.Database;
import com.imss.sivimss.contratocvpps.util.LogUtil;
import com.imss.sivimss.contratocvpps.util.ProviderServiceRestTemplate;
import com.imss.sivimss.contratocvpps.util.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InsertaPlanSfpaRepository {
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Value("${endpoints.ms-reportes}")
	private String urlReportes;
	
	@Value("${reporte.convenio-pago-anticipado}")
	private String reporteConvenioPagoAnticipado;

	@Autowired
	private LogUtil logUtil;
	
	@Autowired
	private Database database;
	
	private Statement statement;
	
	private ResultSet rs;
	
	private Response<Object> response;
	
	public PlanSFPAResponse insertarPlanSfpa(InsertPlanSfpaRequest request) throws Exception {
		ArrayList<String> inserciones = request.getInsertar();
		ArrayList<String> updates = request.getActualizar();
		PlanSFPAResponse planSFPAResponse = new PlanSFPAResponse();
		Integer id=1;
		Connection connection = database.getConnection();
		Integer idTabla1=0;
		Integer idTabla2=0;
		Integer idTabla3=0;
		Integer idTabla4=0;
		Integer i=0;
		
		try {
			
			log.info(" inserciones: " + inserciones);
			log.info(" updates: " + updates);
			
			connection.setAutoCommit(false);
			
			 if (inserciones.size() == 7 && updates.isEmpty()) {//listo
				 planSFPAResponse = accionInserta(inserciones, updates, id, connection, idTabla1, idTabla2, idTabla3, idTabla4, i);
			} else  if (inserciones.size() == 4 && updates.size() == 2) {//listo
				planSFPAResponse = accionInserta1(inserciones, updates, id, connection, idTabla1, idTabla2, idTabla3, idTabla4, i);
			}  else  if (inserciones.size() == 1 && updates.size() == 4) {//listo
				planSFPAResponse = accionInserta(inserciones, updates, id, connection, idTabla1, idTabla2, idTabla3, idTabla4, i);
			} else  if (inserciones.size() == 4 && updates.isEmpty()) {//listo
				planSFPAResponse = accionInserta1(inserciones, updates, id, connection, idTabla1, idTabla2, idTabla3, idTabla4, i);
			} else  if (inserciones.size() == 1 && updates.size() == 2) {//listo
				planSFPAResponse = accionInserta(inserciones, updates, id, connection, idTabla1, idTabla2, idTabla3, idTabla4, i);
			}
			
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), e.getMessage(), ConsultaConstantes.FALLO_QUERY);
			throw new Exception(ConsultaConstantes.FALLO_QUERY + e.getMessage());
		}finally{
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), "", ConsultaConstantes.CIERRA_CONEXION_A_LA_BASE_DE_DATOS);
			try {
				if(statement!=null) statement.close();                                
				if(connection!=null) connection.close();
			} catch (SQLException ex) {
				logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), ex.getMessage(), ConsultaConstantes.FALLO_QUERY);
			}
		}
		return planSFPAResponse;
	}

	public Response<Object> actualizarPlanSfpa(InsertPlanSfpaRequest request) throws Exception {
		ArrayList<String> inserciones = request.getInsertar();
		ArrayList<String> updates = request.getActualizar();
		Integer id=1;
		Connection connection = database.getConnection();
		Integer idTabla1=0;
		Integer idTabla2=0;
		Integer idTabla3=0;
		Integer idTabla4=0;
		Integer i=0;
		
		try {
			
			log.info(" inserciones: " + inserciones);
			log.info(" updates: " + updates);
			
			connection.setAutoCommit(false);
			
			if(inserciones.size() == 7) {
				response = actualizaAccion1(inserciones, updates, id, connection, idTabla1, idTabla2, idTabla3, idTabla4, i);
			} else if(inserciones.size() == 6) {
				response = actualizaAccion2(inserciones, updates, id, connection, idTabla1, idTabla2, idTabla3, idTabla4, i);
			} else if(inserciones.size() == 5) {
				response = actualizaAccion3(inserciones, updates, id, connection, idTabla1, idTabla2, idTabla3, idTabla4, i);
			} else if(inserciones.size() == 4) {
				response = actualizaAccion3(inserciones, updates, id, connection, idTabla1, idTabla2, idTabla3, idTabla4, i);
			}
			
			connection.commit();
			
		} catch (Exception e) {
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), e.getMessage(), ConsultaConstantes.FALLO_QUERY);
			throw new Exception(ConsultaConstantes.FALLO_QUERY + e.getMessage());
		}finally{
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), "", ConsultaConstantes.CIERRA_CONEXION_A_LA_BASE_DE_DATOS);
			try {
				if(statement!=null) statement.close();                                
				if(connection!=null) connection.close();
			} catch (SQLException ex) {
				logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), ex.getMessage(), ConsultaConstantes.FALLO_QUERY);
			}
		}
		return response;
	}
	
	public Response<Object> cancelarPlanSfpa(String request) throws Exception {
		Connection connection = database.getConnection();
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
    		Integer r = statement.executeUpdate(request);
    		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.MODIFICACION+" "+ request);
    		connection.commit();
    		if(r == 1) {
    			response= new Response<>(false, 200, "18");
    		} else {
    			response= new Response<>(false, 200, "45");
    		}
		} catch (Exception e) {
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), e.getMessage(), ConsultaConstantes.FALLO_QUERY);
			throw new Exception(ConsultaConstantes.FALLO_QUERY + e.getMessage());
		} finally {
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), "", ConsultaConstantes.CIERRA_CONEXION_A_LA_BASE_DE_DATOS);
			try {
				if(statement!=null) statement.close();
				if(rs!=null) rs.close();
				if(connection!=null) connection.close();
			} catch (SQLException ex) {
				logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), ex.getMessage(), ConsultaConstantes.FALLO_QUERY);
			}
		}
		return response;
	}
	
	public Response<Object> consultarNumeroPagoPlanSfpa(String request) throws Exception {
		Connection connection = database.getConnection();
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			rs=statement.executeQuery(request);
    		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.MODIFICACION+" "+ request);
    		connection.commit();
    		if (rs.next()) {
    			NumeroPagoPlanSfpaResponse numeroPagoPlanSfpaResponse = new NumeroPagoPlanSfpaResponse();
    			numeroPagoPlanSfpaResponse.setNumeroPagoPlanSfpa(rs.getInt(1));
				response= new Response<>(false, 200, "EXITO", ConvertirGenerico.convertInstanceOfObject(numeroPagoPlanSfpaResponse));
    		} else {
    			response= new Response<>(false, 200, "45", "[]");
    		}
		} catch (Exception e) {
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), e.getMessage(), ConsultaConstantes.FALLO_QUERY);
			throw new Exception(ConsultaConstantes.FALLO_QUERY + e.getMessage());
		} finally {
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), "", ConsultaConstantes.CIERRA_CONEXION_A_LA_BASE_DE_DATOS);
			try {
				if(statement!=null) statement.close();
				if(rs!=null) rs.close();
				if(connection!=null) connection.close();
			} catch (SQLException ex) {
				logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(), ex.getMessage(), ConsultaConstantes.FALLO_QUERY);
			}
		}
		return response;
	}
	
	public Response<Object> generarReporteDonacion(ReporteRequest reporteRequest, Authentication authentication)
			throws IOException, SQLException {
		Connection connection = database.getConnection();
		statement = connection.createStatement();
		connection.setAutoCommit(false);
		Map<String, Object> envioDatos = new HashMap<>();
		envioDatos.put("condition", "AND R.ID_ROL = 17 AND R.DES_ROL = 'APOYO ADMINISTRATIVO' ORDER BY R.ID_ROL");
		envioDatos.put(ConsultaConstantes.TIPO_REPORTE, "pdf");
		envioDatos.put(ConsultaConstantes.RUTA_NOMBRE_REPORTE, reporteConvenioPagoAnticipado);
		try {
			log.info(ConsultaConstantes.CU067_NOMBRE + ConsultaConstantes.GENERAR_DOCUMENTO + " Reporte Convenio Pago Anticipado " );
			logUtil.crearArchivoLog(Level.INFO.toString(), ConsultaConstantes.CU067_NOMBRE + ConsultaConstantes.GENERAR_DOCUMENTO + " Reporte Convenio Pago Anticipado " + this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(), "generarReporteConvenioPagoAnticipado", ConsultaConstantes.GENERA_DOCUMENTO, authentication);
			response = providerRestTemplate.consumirServicioReportes(envioDatos, urlReportes, authentication);
			return   response;
		} catch (Exception e) {
			log.error(ConsultaConstantes.CU067_NOMBRE + ConsultaConstantes.GENERAR_DOCUMENTO);
			logUtil.crearArchivoLog(Level.WARNING.toString(), ConsultaConstantes.CU067_NOMBRE + ConsultaConstantes.GENERAR_DOCUMENTO + this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(),"", ConsultaConstantes.GENERA_DOCUMENTO,
					authentication);
			throw new IOException("52", e.getCause());
		} finally {
				statement.close();
				connection.close();
		}
	}
	
	private PlanSFPAResponse accionInserta(ArrayList<String> inserciones, ArrayList<String> updates, Integer id, Connection connection,
			Integer idTabla1, Integer idTabla2, Integer idTabla3,Integer idTabla4, Integer i) throws IOException, SQLException {
		PlanSFPAResponse planSFPAResponse = new PlanSFPAResponse();
		log.info("Entro accionInserta");
		for( String insercion : updates ) {
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.MODIFICACION+" "+ insercion);
			statement = connection.createStatement();
			statement.executeUpdate(insercion, Statement.RETURN_GENERATED_KEYS);
	 }

		for( String insercion : inserciones) {
			if( i.equals(2) || i.equals(4) || i.equals(5) ) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA1, idTabla1.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA2, idTabla2.toString());
			} else if(i.equals(3) || i.equals(5) || i.equals(6)) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA3, idTabla3.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA4, idTabla4.toString());
			}
			
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.ALTA+" "+ insercion);
			statement = connection.createStatement();
			statement.executeUpdate(insercion, Statement.RETURN_GENERATED_KEYS);
			rs=statement.getGeneratedKeys();
			
			if(rs.next()){
				id=rs.getInt(1);
				logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.ALTA+" "+ id.toString());
				planSFPAResponse.setIdPlanSfpa(id);
			}
			
			if( i.equals(0)|| i.equals(3) ) {
				idTabla1 = id;
			}else if( i.equals(1)|| i.equals(4) ) {
				idTabla2 = id;
			}else if(i.equals(2)) {
				idTabla3 = id;
			} else if(i.equals(5)) {
				idTabla4 = id;
			}
			i++;
		}
		
		return planSFPAResponse;
	}
	
	private PlanSFPAResponse accionInserta1(ArrayList<String> inserciones, ArrayList<String> updates, Integer id, Connection connection,
			Integer idTabla1, Integer idTabla2, Integer idTabla3, Integer idTabla4, Integer i) throws IOException, SQLException {
		log.info("Entro accionInserta1");
		PlanSFPAResponse planSFPAResponse = new PlanSFPAResponse();
		for( String insercion : updates ) {
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.MODIFICACION+" "+ insercion);
			statement = connection.createStatement();
			statement.executeUpdate(insercion, Statement.RETURN_GENERATED_KEYS);
	 }
		
		for( String insercion : inserciones) {
			if( i.equals(2)) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA1, idTabla1.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA2, idTabla2.toString());
			} else if(i.equals(3) ) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA3, idTabla3.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA4, idTabla4.toString());
			}
			
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.ALTA+" "+ insercion);
			statement = connection.createStatement();
			statement.executeUpdate(insercion, Statement.RETURN_GENERATED_KEYS);
			rs=statement.getGeneratedKeys();
			
			if(rs.next()){
				id=rs.getInt(1);
				logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.ALTA+" "+ id.toString());
				planSFPAResponse.setIdPlanSfpa(id);
			}
			
			if( i.equals(0)) {
				idTabla1 = id;
			}else if( i.equals(1)) {
				idTabla2 = id;
			}else if(i.equals(2)) {
				idTabla3 = id;
				idTabla4 = id;
			}
			i++;
		}
		return planSFPAResponse;
	}
	
	private Response<Object> actualizaAccion1(ArrayList<String> inserciones, ArrayList<String> updates, Integer id, Connection connection,
			Integer idTabla1, Integer idTabla2, Integer idTabla3,Integer idTabla4, Integer i) throws IOException, SQLException {
		log.info("Entro actualizaAccion1");
		PlanSFPAResponse planSFPAResponse = new PlanSFPAResponse();
		for( String insercion : inserciones) {
			
			if( i.equals(2) || i.equals(5) ) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA1, idTabla1.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA2, idTabla2.toString());
			} else if(i.equals(6)) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA3, idTabla3.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA4, idTabla4.toString());
			}
			
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.ALTA+" "+ insercion);
			statement = connection.createStatement();
			statement.executeUpdate(insercion, Statement.RETURN_GENERATED_KEYS);
			rs=statement.getGeneratedKeys();
			
			if(rs.next()){
				id=rs.getInt(1);
				logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.ALTA+" "+ id.toString());
				planSFPAResponse.setIdPlanSfpa(id);
				response= new Response<>(false, 200, ConsultaConstantes.EXITO, ConvertirGenerico.convertInstanceOfObject(planSFPAResponse));
			}
			
			if( i.equals(0) ||  i.equals(3)) {
				idTabla1 = id;
			}else if( i.equals(1) || i.equals(4)) {
				idTabla2 = id;
			}else if(i.equals(2)) {
				idTabla3 = id;
			} else if(i.equals(5)) {
				idTabla4 = id;
			}
			i++;
		}
		response= new Response<>(false, 200, ConsultaConstantes.EXITO, "");
		
		return response;
	}
	
	private Response<Object> actualizaAccion2(ArrayList<String> inserciones, ArrayList<String> updates, Integer id, Connection connection,
			Integer idTabla1, Integer idTabla2, Integer idTabla3,Integer idTabla4, Integer i) throws IOException, SQLException {
		log.info("Entro actualizaAccion2");
		
		for( String insercion : inserciones) {
			
			if(i.equals(2) ||  i.equals(4) ) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA1, idTabla1.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA2, idTabla2.toString());
			} else if(i.equals(5)) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA3, idTabla3.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA4, idTabla4.toString());
			}
			
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.ALTA+" "+ insercion);
			statement = connection.createStatement();
			statement.executeUpdate(insercion, Statement.RETURN_GENERATED_KEYS);
			rs=statement.getGeneratedKeys();
			
			if(rs.next()){
				id=rs.getInt(1);
				logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.ALTA+" "+ id.toString());
				PlanSFPAResponse planSFPAResponse = new  PlanSFPAResponse();
				planSFPAResponse.setIdPlanSfpa(rs.getInt(1));
				response= new Response<>(false, 200, ConsultaConstantes.EXITO, ConvertirGenerico.convertInstanceOfObject(planSFPAResponse));
			}
			
			if( i.equals(0)) {
				idTabla1 = id;
			}else if( i.equals(1)) {
				idTabla2 = id;
			}else if(i.equals(4)) {
				idTabla3 = id;
				idTabla4 = id;
			}
			i++;
		}
		response= new Response<>(false, 200, ConsultaConstantes.EXITO, "");
		
		return response;
	}
	
	private Response<Object> actualizaAccion3(ArrayList<String> inserciones, ArrayList<String> updates, Integer id, Connection connection,
			Integer idTabla1, Integer idTabla2, Integer idTabla3,Integer idTabla4, Integer i) throws IOException, SQLException {
		log.info("Entro actualizaAccion3");
		
		for( String insercion : inserciones) {
			
			if( i.equals(2) ) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA1, idTabla1.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA2, idTabla2.toString());
			} else if(i.equals(3)) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA3, idTabla3.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA4, idTabla4.toString());
			}
			
			logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.ALTA+" "+ insercion);
			statement = connection.createStatement();
			statement.executeUpdate(insercion, Statement.RETURN_GENERATED_KEYS);
			rs=statement.getGeneratedKeys();
			
			if(rs.next()){
				id=rs.getInt(1);
				logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.ALTA+" "+ id.toString());
				PlanSFPAResponse planSFPAResponse = new  PlanSFPAResponse();
				planSFPAResponse.setIdPlanSfpa(rs.getInt(1));
				response= new Response<>(false, 200, ConsultaConstantes.EXITO, ConvertirGenerico.convertInstanceOfObject(planSFPAResponse));
			}
			
			if( i.equals(0)) {
				idTabla1 = id;
			}else if( i.equals(1)) {
				idTabla2 = id;
			}else if(i.equals(2)) {
				idTabla3 = id;
			}
			i++;
		}
		response= new Response<>(false, 200, ConsultaConstantes.EXITO, "");
		
		return response;
	}
}
