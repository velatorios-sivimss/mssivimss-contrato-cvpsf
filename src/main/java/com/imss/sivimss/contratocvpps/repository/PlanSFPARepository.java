package com.imss.sivimss.contratocvpps.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.imss.sivimss.contratocvpps.model.response.LineaPlanSFPAResponse;
import com.imss.sivimss.contratocvpps.model.response.NumeroPagoPlanSfpaResponse;
import com.imss.sivimss.contratocvpps.model.response.PagoFechaResponse;
import com.imss.sivimss.contratocvpps.model.response.PersonaResponse;
import com.imss.sivimss.contratocvpps.model.response.PersonaTitularBeneficiariosResponse;
import com.imss.sivimss.contratocvpps.model.response.PlanSFPAResponse;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.ConsultaConstantes;
import com.imss.sivimss.contratocvpps.util.ConvertirGenerico;
import com.imss.sivimss.contratocvpps.util.Database;
import com.imss.sivimss.contratocvpps.util.LogUtil;
import com.imss.sivimss.contratocvpps.util.Response;

@Service
public class PlanSFPARepository {
	
	@Value("${endpoints.ms-reportes}")
	private String urlReportes;

	@Autowired
	private LogUtil logUtil;
	
	@Autowired
	private Database database;
	
	private Statement statement;
	
	private ResultSet rs;
	
	private Response<Object> response;
	
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
	
	public Response<Object> consultarPlanSfpa(String request) throws Exception {
		Connection connection = database.getConnection();
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			rs=statement.executeQuery(request);
    		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.CONSULTA+" "+ request);
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
	
	public Response<Object> consultarDetallePlanSfpa(String request) throws Exception {
		Connection connection = database.getConnection();
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			rs=statement.executeQuery(request);
    		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.CONSULTA+" "+ request);
    		connection.commit();
    		if (rs.next()) {
    			PlanSFPAResponse planSFPAResponse = ConsultaConstantes.generarDetallePlan(rs);
				response= new Response<>(false, 200, "EXITO", ConvertirGenerico.convertInstanceOfObject(planSFPAResponse));
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
	
	public PersonaResponse datoContratante(String request) throws Exception {
		Connection connection = database.getConnection();
		PersonaResponse personaResponse = null;
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			rs=statement.executeQuery(request);
    		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.CONSULTA+" "+ request);
    		connection.commit();
    		if (rs.next()) {
    			personaResponse = new PersonaResponse();
    			personaResponse.setIdContratante(rs.getInt("idContratante"));
    			personaResponse.setIdDomicilio(rs.getInt("idDomicilio"));
    			personaResponse.setIdPersona(rs.getInt("idPersona"));
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
		return personaResponse;
	}
	
	public PersonaTitularBeneficiariosResponse datoTitularBeneficiarios(String request) throws Exception {
		Connection connection = database.getConnection();
		PersonaTitularBeneficiariosResponse personaResponse = null;
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			rs=statement.executeQuery(request);
    		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.CONSULTA+" "+ request);
    		connection.commit();
    		if (rs.next()) {
    			personaResponse = new PersonaTitularBeneficiariosResponse();
    			personaResponse.setIdTitularBeneficiarios(rs.getInt("idTitularBeneficiarios"));
    			personaResponse.setIdDomicilio(rs.getInt("idDomicilio"));
    			personaResponse.setIdPersona(rs.getInt("idPersona"));
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
		return personaResponse;
	}
	
	public PagoFechaResponse consultarFechaPagoSfpa(String request) throws Exception {
		Connection connection = database.getConnection();
		PagoFechaResponse pagoFechaResponse = null;
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			rs=statement.executeQuery(request);
    		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.CONSULTA+" "+ request);
    		connection.commit();
    		if (rs.next()) {
    			pagoFechaResponse = new PagoFechaResponse();
    			pagoFechaResponse.setFechaParcialidad(rs.getString("FEC_PARCIALIDAD"));
    			pagoFechaResponse.setFechaAlta(rs.getString("FEC_PARCIALIDAD"));
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
		return pagoFechaResponse;
	}
	
	public boolean  detelePagoSfpa(String request) throws Exception {
		Connection connection = database.getConnection();
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			rs=statement.executeQuery(request);
    		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.DELETE+" "+ request);
    		connection.commit();
    		return true;
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
	}
	
	public String obtenerFolioPlanSfpa(String request) throws Exception {
		Connection connection = database.getConnection();
		String folio=null;
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			rs=statement.executeQuery(request);
    		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.CONSULTA+" "+ request);
    		connection.commit();
    		if (rs.next()) {
    			folio=rs.getString("folioPlanSFPA");
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
		return folio;
	}
	
	public Response<Object> consultarLineaDetallePlanSfpa(String request) throws Exception {
		Connection connection = database.getConnection();
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			rs=statement.executeQuery(request);
    		logUtil.crearArchivoLog(Level.INFO.toString(),this.getClass().getSimpleName(),this.getClass().getPackage().toString(),"",AppConstantes.CONSULTA+" "+ request);
    		connection.commit();
    		if (rs.next()) {
    			LineaPlanSFPAResponse lineaPlanSFPAResponse = ConsultaConstantes.generarDetalleLineaPlan(rs);
				response= new Response<>(false, 200, "EXITO", ConvertirGenerico.convertInstanceOfObject(lineaPlanSFPAResponse));
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
}
