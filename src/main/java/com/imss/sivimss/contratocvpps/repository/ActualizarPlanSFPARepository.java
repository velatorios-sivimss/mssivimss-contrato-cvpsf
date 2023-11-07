package com.imss.sivimss.contratocvpps.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imss.sivimss.contratocvpps.model.request.InsertPlanSfpaRequest;
import com.imss.sivimss.contratocvpps.model.response.PlanSFPAResponse;
import com.imss.sivimss.contratocvpps.util.AppConstantes;
import com.imss.sivimss.contratocvpps.util.ConsultaConstantes;
import com.imss.sivimss.contratocvpps.util.ConvertirGenerico;
import com.imss.sivimss.contratocvpps.util.Database;
import com.imss.sivimss.contratocvpps.util.LogUtil;
import com.imss.sivimss.contratocvpps.util.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActualizarPlanSFPARepository {

	@Autowired
	private Database database;

	@Autowired
	private LogUtil logUtil;

	private Statement statement;
	
	private Response<Object> response;

	public Response<Object> actulizaPlanSfpa(InsertPlanSfpaRequest request) throws Exception {
		ArrayList<String> inserciones = request.getInsertar();
		ArrayList<String> updates = request.getActualizar();
		Connection connection = database.getConnection();
		Integer idTabla1 = 0;
		Integer idTabla2 = 0;
		Integer idTabla3 = 0;
		Integer idTabla4 = 0;
		Integer idTabla5 = 0;
		Integer idTabla6 = 0;
		Integer idTabla7 = 0;
		Integer id = 1;
		Integer i = 0;
		try {

			log.info(" inserciones: " + inserciones);
			log.info(" updates: " + updates);

			connection.setAutoCommit(false);

			if ((inserciones.size() == 12 || inserciones.size() == 9 || inserciones.size() == 6 || inserciones.size() == 3||  inserciones.isEmpty()) && (updates.size() == 1 || updates.size() == 4 || updates.size() == 7 || updates.size() == 10 || updates.size() == 13)) {// listo
				response  = accionActualiza(request, id, connection, idTabla1, idTabla2, idTabla3,idTabla4, idTabla5, idTabla6, idTabla7, i);
			}

			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(), e.getMessage(), ConsultaConstantes.FALLO_QUERY);
			throw new Exception(ConsultaConstantes.FALLO_QUERY + e.getMessage());
		} finally {
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
					this.getClass().getPackage().toString(), "", ConsultaConstantes.CIERRA_CONEXION_A_LA_BASE_DE_DATOS);
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),
						this.getClass().getPackage().toString(), ex.getMessage(), ConsultaConstantes.FALLO_QUERY);
			}
		}
		return response;
	}

	private Response<Object> accionActualiza(InsertPlanSfpaRequest request, Integer id,
			Connection connection, Integer idTabla1, Integer idTabla2, Integer idTabla3, Integer idTabla4,
			Integer idTabla5, Integer idTabla6, Integer idTabla7, Integer i) throws IOException, SQLException {
		ResultSet rs;
		PlanSFPAResponse planSFPAResponse = new PlanSFPAResponse();
		log.info("Entro accionInserta");
		for (String insercion : request.getActualizar()) {
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), "", AppConstantes.MODIFICACION + " " + insercion);
			statement = connection.createStatement();
			statement.executeUpdate(insercion, Statement.RETURN_GENERATED_KEYS);
		}

		for (String insercion : request.getInsertar()) {
			if (i.equals(2) || i.equals(4) || i.equals(5) || i.equals(8) || i.equals(11)) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA1, idTabla1.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA2, idTabla2.toString());
			} else if (i.equals(3) || i.equals(6) || i.equals(9) || i.equals(12)) {
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA3, idTabla3.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA4, idTabla4.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA5, idTabla5.toString());
				insercion = insercion.replace(ConsultaConstantes.ID_TABLA6, idTabla6.toString());
			}

			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), "", AppConstantes.ALTA + " " + insercion);
			statement = connection.createStatement();
			statement.executeUpdate(insercion, Statement.RETURN_GENERATED_KEYS);
			rs = statement.getGeneratedKeys();

			if (rs.next()) {
				id = rs.getInt(1);
				logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), "", AppConstantes.ALTA + " " + id.toString());
				planSFPAResponse.setIdPlanSfpa(id);
				response= new Response<>(false, 200, ConsultaConstantes.EXITO, ConvertirGenerico.convertInstanceOfObject(planSFPAResponse));
			}

			if (i.equals(0) || i.equals(3) || i.equals(6) || i.equals(9)) {
				idTabla1 = id;
			} else if (i.equals(1) || i.equals(4) || i.equals(7) || i.equals(10)) {
				idTabla2 = id;
			} else if (i.equals(2)) {
				idTabla3 = id;
			} else if (i.equals(5)) {
				idTabla4 = id;
			} else if (i.equals(8)) {
				idTabla5 = id;
			} else if (i.equals(11)) {
				idTabla6 = id;
			}
			i++;
		}
		
		for (String insercion : request.getInsertar2()) {
			insercion = insercion.replace(ConsultaConstantes.ID_TABLA7, idTabla7.toString());
			logUtil.crearArchivoLog(Level.INFO.toString(), this.getClass().getSimpleName(),this.getClass().getPackage().toString(), "", AppConstantes.ALTA + " " + insercion);
			statement = connection.createStatement();
			statement.executeUpdate(insercion, Statement.RETURN_GENERATED_KEYS);
			i++;
		}
		response= new Response<>(false, 200, ConsultaConstantes.EXITO, "");
		
		return response;
	}

}
