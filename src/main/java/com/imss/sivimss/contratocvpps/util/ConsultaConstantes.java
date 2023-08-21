package com.imss.sivimss.contratocvpps.util;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.imss.sivimss.contratocvpps.model.request.ContratanteRequest;
import com.imss.sivimss.contratocvpps.model.request.DomicilioRequest;
import com.imss.sivimss.contratocvpps.model.response.PlanSFPAResponse;

public class ConsultaConstantes {
	
	public static final String CIERRA_CONEXION_A_LA_BASE_DE_DATOS = "cierra conexion a la base de datos";
	
	public static final String ESTATUS_ORDEN_SERVICIO = "estatusOrdenServicio";
	public static final String OS_CVE_FOLIO_CVE_FOLIO = "OS.CVE_FOLIO = :cveFolio";
	public static final String NOM_SEGUNDO_APELLIDO = "NOM_SEGUNDO_APELLIDO";
	public static final String SVC_ORDEN_SERVICIO_OS = "SVC_ORDEN_SERVICIO OS";
	public static final String RUTA_NOMBRE_REPORTE = "rutaNombreReporte";
	public static final String ID_ESTATUS_PLAN_SFPA = "ID_ESTATUS_PLAN_SFPA";
	public static final String ID_TITULAR_SUBSTITUTO = "ID_TITULAR_SUBSTITUTO";
	public static final String SVT_PLAN_SFPA_SPSFPA = "SVT_PLAN_SFPA SPSFPA";
	public static final String NOM_PRIMER_APELLIDO = "NOM_PRIMER_APELLIDO";
	public static final String RESPONSABLE_ALMACEN = "responsableAlmacen";
	public static final String ID_USUARIO_MODIFICA = "ID_USUARIO_MODIFICA";
	public static final String GENERAR_DOCUMENTO = "Generar Reporte: " ;
	public static final String GENERA_DOCUMENTO = "Genera_Documento";
	public static final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP()";
	public static final String FEC_ACTUALIZACION = "FEC_ACTUALIZACION";
	public static final String ID_BENEFICIARIO_2 = "ID_BENEFICIARIO_2";
	public static final String ID_BENEFICIARIO_1 = "ID_BENEFICIARIO_1";
	public static final String AND_CVE_ESTATUS = "OS.ID_ESTATUS_ORDEN_SERVICIO = :estatusOrdenServicio";
	public static final String ID_USUARIO_ALTA = "ID_USUARIO_ALTA";
	public static final String CU067_NOMBRE= "Consulta convenio Pago Anticipado: ";
	public static final String TIPO_REPORTE = "tipoReporte";
	public static final String FALLO_QUERY = "Fallo al ejecutar el Query  ";
	public static final String ID_ARTICULO = "ID_ARTICULO";
	public static final String ID_TITULAR = "ID_TITULAR";
	public static final String CVE_FOLIO = "cveFolio";
	public static final String SEPARADOR = "separador";
	public static final String ID_TABLA1 = "idTabla1";
	public static final String ID_TABLA2 = "idTabla2";
	public static final String ID_TABLA4 = "idTabla4";
	public static final String ID_TABLA3 = "idTabla3";
	public static final String FEC_ALTA = "FEC_ALTA";
	public static final String ID_TABLA = "idTabla";
	public static final String AFILIADO = "afiliado";
	public static final String REPLACE = "replace";
	public static final String QUERY = " query : ";
	public static final String EXITO = "Exito"; 
	public static final String COMILLA_SIMPLE = "'";
	
	private ConsultaConstantes() {
		super();
	}
	
	public static SelectQueryUtil detalleContratante () {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("P.ID_PERSONA AS idPersona","P.CVE_RFC AS rfc","P.CVE_CURP AS curp","P.CVE_NSS AS nss","P.NOM_PERSONA AS nomPersona","P.NOM_PRIMER_APELLIDO AS nomPersonaPaterno",
				"P.NOM_SEGUNDO_APELLIDO AS nomPersonaMaterno","P.NUM_SEXO AS numSexo","P.DES_OTRO_SEXO AS desOtroSexo","P.FEC_NAC as fechaNacimiento","P.ID_PAIS AS idPais","P.ID_ESTADO AS idEstado","P.DES_TELEFONO AS desTelefono",
				"P.DES_CORREO AS desCorreo","P.TIP_PERSONA AS tipoPersona","C.ID_CONTRATANTE AS idContratante","C.CVE_MATRICULA AS claveMatricula","D.DES_CALLE AS desCalle","D.NUM_EXTERIOR AS numExterior",
				"D.NUM_INTERIOR AS numInterior","D.DES_CP AS DesCodigoPostal","D.DES_COLONIA AS desColonia","D.DES_MUNICIPIO AS desMunicipio","D.DES_ESTADO AS desEstado")
		.from("SVC_PERSONA P")
		.innerJoin("SVC_CONTRATANTE C", "P.ID_PERSONA = C.ID_PERSONA")
		.innerJoin("SVT_DOMICILIO D", "C.ID_DOMICILIO = D.ID_DOMICILIO");
		return queryUtil;
	}
	
	public static Integer getIdVelatorio(Integer idVelatorio) {
		if(idVelatorio == null){
			return 0;
		}
		return idVelatorio;
	}
	
	public static  PlanSFPAResponse generarDetallePlan(ResultSet rs) throws SQLException {
		PlanSFPAResponse planSFPAResponse = new PlanSFPAResponse();
		List<ContratanteRequest> titularesBeneficiarios = new ArrayList<>(); 
		ContratanteRequest contratanteRequest = new ContratanteRequest();
		DomicilioRequest cp = new DomicilioRequest ();
		planSFPAResponse.setIdPlanSfpa(rs.getInt(1));
		planSFPAResponse.setNumFolioPlanSFPA(rs.getString(2));
		planSFPAResponse.setIdTipoContratacion(rs.getInt(3));
		planSFPAResponse.setIdPaquete(rs.getInt(4));
		planSFPAResponse.setIdTipoPagoMensual(rs.getInt(5));
		planSFPAResponse.setIndTitularSubstituto(rs.getInt(6));
		planSFPAResponse.setIndModificarTitularSubstituto(rs.getInt(7));
		planSFPAResponse.setDesIdVelatorio(rs.getString(56));
		planSFPAResponse.setFecIngreso(rs.getString(57));
		contratanteRequest.setPersona(AFILIADO);
		contratanteRequest.setRfc(rs.getString(8));
		contratanteRequest.setCurp(rs.getString(9));
		contratanteRequest.setMatricula(rs.getString(10));
		contratanteRequest.setNss(rs.getString(11));
		contratanteRequest.setNomPersona(rs.getString(12));
		contratanteRequest.setPrimerApellido(rs.getString(13));
		contratanteRequest.setSegundoApellido(rs.getString(14));
		contratanteRequest.setSexo(rs.getString(15));
		contratanteRequest.setOtroSexo(rs.getString(16));
		contratanteRequest.setFecNacimiento(rs.getString(17));
		contratanteRequest.setIdPais(rs.getInt(18));
		contratanteRequest.setIdEstado(rs.getInt(19));
		contratanteRequest.setTelefono(rs.getString(20));
		contratanteRequest.setTelefonoFijo(rs.getString(21));
		contratanteRequest.setCorreo(rs.getString(22));
		contratanteRequest.setTipoPersona(rs.getString(23));
		contratanteRequest.setIne(rs.getString(24));
		cp.setDesCalle(rs.getString(25));
		cp.setNumExterior(rs.getString(26));
		cp.setNumInterior(rs.getString(27));
		cp.setCodigoPostal(rs.getInt(28));
		cp.setDesColonia(rs.getString(29));
		cp.setDesMunicipio(rs.getString(30));
		cp.setDesEstado(rs.getString(31));
		contratanteRequest.setCp(cp);
		titularesBeneficiarios.add(contratanteRequest);
		if(rs.getInt(6) == 0){
			ContratanteRequest contratanteRequest2 = new ContratanteRequest();
			DomicilioRequest cp2 = new DomicilioRequest ();
			contratanteRequest2.setPersona("contratante");
			contratanteRequest2.setRfc(rs.getString(32));
			contratanteRequest2.setCurp(rs.getString(33));
			contratanteRequest2.setMatricula(rs.getString(34));
			contratanteRequest2.setNss(rs.getString(35));
			contratanteRequest2.setNomPersona(rs.getString(36));
			contratanteRequest2.setPrimerApellido(rs.getString(37));
			contratanteRequest2.setSegundoApellido(rs.getString(38));
			contratanteRequest2.setSexo(rs.getString(39));
			contratanteRequest2.setOtroSexo(rs.getString(40));
			contratanteRequest2.setFecNacimiento(rs.getString(41));
			contratanteRequest2.setIdPais(rs.getInt(42));
			contratanteRequest2.setIdEstado(rs.getInt(43));
			contratanteRequest2.setTelefono(rs.getString(44));
			contratanteRequest2.setTelefonoFijo(rs.getString(45));
			contratanteRequest2.setCorreo(rs.getString(46));
			contratanteRequest2.setTipoPersona(rs.getString(47));
			contratanteRequest2.setIne(rs.getString(48));
			cp2.setDesCalle(rs.getString(49));
			cp2.setNumExterior(rs.getString(50));
			cp2.setNumInterior(rs.getString(51));
			cp2.setCodigoPostal(rs.getInt(52));
			cp2.setDesColonia(rs.getString(53));
			cp2.setDesMunicipio(rs.getString(54));
			cp2.setDesEstado(rs.getString(55));
			contratanteRequest2.setCp(cp2);
			titularesBeneficiarios.add(contratanteRequest2);
		}
		planSFPAResponse.setTitularesBeneficiarios(titularesBeneficiarios);
		return planSFPAResponse;
	}
	
	public static String queryEncoded (String query) {
		return DatatypeConverter.printBase64Binary(query.getBytes(StandardCharsets.UTF_8));
	}
}
