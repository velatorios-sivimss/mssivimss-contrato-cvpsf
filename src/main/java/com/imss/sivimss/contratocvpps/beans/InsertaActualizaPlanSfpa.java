package com.imss.sivimss.contratocvpps.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.imss.sivimss.contratocvpps.model.request.ContratanteRequest;
import com.imss.sivimss.contratocvpps.model.request.InsertPlanSfpaRequest;
import com.imss.sivimss.contratocvpps.model.request.PlanSFPARequest;
import com.imss.sivimss.contratocvpps.model.request.UsuarioDto;
import com.imss.sivimss.contratocvpps.model.response.PagoFechaResponse;
import com.imss.sivimss.contratocvpps.util.ConsultaConstantes;
import com.imss.sivimss.contratocvpps.util.QueryHelper;
import com.imss.sivimss.contratocvpps.util.SelectQueryUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InsertaActualizaPlanSfpa  implements Serializable {
	
	
	private static final String CVE_MATRICULA = "CVE_MATRICULA";
	private static final String IND_ACTIVO = "IND_ACTIVO";
	private static final String ID_DOMICILIO = "ID_DOMICILIO";
	private static final String ID_PERSONA = "ID_PERSONA";
	private static final long serialVersionUID = 1L;
	
	public InsertPlanSfpaRequest insertaPlanSfpa(PlanSFPARequest planSFPARequest, PagoFechaResponse pagoFechaResponse, UsuarioDto usuarioDto) {
		log.info(" INICIO - insertaPlanSfpa");
		String query = "";
		Map<String, String> map = new HashMap<>();
		map.put(ConsultaConstantes.ID_BENEFICIARIO_1,  null);
		map.put(ConsultaConstantes.ID_BENEFICIARIO_2,  null);
		
		InsertPlanSfpaRequest insertPlanSfpaRequest = new InsertPlanSfpaRequest();
		ArrayList<String> insertar = new ArrayList<>();
		ArrayList<String> actualizar = new ArrayList<>();
		ArrayList<String> insertar2 = new ArrayList<>();
		if (planSFPARequest.getIdPlanSfpa()!= null) {
			for(ContratanteRequest contratanteRequest: planSFPARequest.getTitularesBeneficiarios()) {
				log.info("  idPersona:  " + contratanteRequest.getIdPersona() +"  idContratante:  "+  contratanteRequest.getIdContratante() + "  IdDomicilio:  "+ contratanteRequest.getCp().getIdDomicilio());
				if(contratanteRequest.getPersona().equalsIgnoreCase(ConsultaConstantes.TITULAR)) {
					if(0 < contratanteRequest.getIdPersona()) {
						insertar.add(updatePersona(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertPersona(contratanteRequest, usuarioDto));
					}
					if(0 < contratanteRequest.getCp().getIdDomicilio()) {
						insertar.add(updateDomicilio(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertDomicilio(contratanteRequest, usuarioDto));
					}
					if(0 < contratanteRequest.getIdContratante()) {
						insertar.add(updateContratante(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertContratante(contratanteRequest, usuarioDto));
					}
				} else {
					if(0 < contratanteRequest.getIdPersona()) {	
						insertar.add(updatePersona(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertPersona(contratanteRequest, usuarioDto));
					}
					if(0 < contratanteRequest.getCp().getIdDomicilio()) {
						insertar.add(updateDomicilio(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertDomicilio(contratanteRequest, usuarioDto));
					}
					if (0 < contratanteRequest.getIdTitularBeneficiarios()) {
						insertar.add(updateTitularBeneficiarios(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertTitularBeneficiarios(contratanteRequest, usuarioDto));
					}
				}
				
				
				map =  obtenerContrante(planSFPARequest, contratanteRequest, map);
			}
			
			if(Boolean.TRUE.equals(planSFPARequest.getIndTipoPagoMensual())) {
				insertar2 = actualizarPagoSFPA(planSFPARequest, pagoFechaResponse, usuarioDto);
			}
			
			query = updatePlanSfpa(planSFPARequest, usuarioDto, map);
			insertar.add(query);
		} else  {
			for(ContratanteRequest contratanteRequest: planSFPARequest.getTitularesBeneficiarios()) {
				if(contratanteRequest.getPersona().equalsIgnoreCase(ConsultaConstantes.TITULAR)) {
					log.info( "TITULAR" +"  idPersona:  " + contratanteRequest.getIdPersona() +"  idContratante:  "+  contratanteRequest.getIdTitularBeneficiarios() + "  IdDomicilio:  "+ contratanteRequest.getCp().getIdDomicilio());
					if(0 < contratanteRequest.getIdPersona()) {
						insertar.add(updatePersona(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertPersona(contratanteRequest, usuarioDto));
					}
					if(0 < contratanteRequest.getCp().getIdDomicilio()) {
						insertar.add(updateDomicilio(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertDomicilio(contratanteRequest, usuarioDto));
					}
					if(0 < contratanteRequest.getIdContratante()) {
						insertar.add(updateContratante(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertContratante(contratanteRequest, usuarioDto));
					}
				} else {
					log.info( contratanteRequest.getPersona() +"  idPersona:  " + contratanteRequest.getIdPersona() +"  idContratante:  "+  contratanteRequest.getIdTitularBeneficiarios() + "  IdDomicilio:  "+ contratanteRequest.getCp().getIdDomicilio());
					if(0 < contratanteRequest.getIdPersona()) {	
						insertar.add(updatePersona(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertPersona(contratanteRequest, usuarioDto));
					}
					if(0 < contratanteRequest.getCp().getIdDomicilio()) {
						insertar.add(updateDomicilio(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertDomicilio(contratanteRequest, usuarioDto));
					}
					if (0 < contratanteRequest.getIdTitularBeneficiarios()) {
						insertar.add(updateTitularBeneficiarios(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertTitularBeneficiarios(contratanteRequest, usuarioDto));
					}
				}

				map = obtenerContrante(planSFPARequest, contratanteRequest, map);
			}
			
			insertar2 = guardarPagoSFPA(planSFPARequest, usuarioDto);
			
			query = insertPlanSfpa(planSFPARequest, usuarioDto, map);
			insertar.add(query);
		}
		
		insertPlanSfpaRequest.setInsertar(insertar);
		insertPlanSfpaRequest.setActualizar(actualizar);
		insertPlanSfpaRequest.setInsertar2(insertar2);
	
		log.info(" TERMINO - insertaPlanSfpa");
		return insertPlanSfpaRequest;
	}


	private String insertPersona(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - insertPersona");
		final QueryHelper q = new QueryHelper("INSERT INTO SVC_PERSONA");
		q.agregarParametroValues("CVE_RFC", SelectQueryUtil.setValor(contratanteRequest.getRfc() ));
		q.agregarParametroValues("CVE_CURP", SelectQueryUtil.setValor(contratanteRequest.getCurp() ));
		q.agregarParametroValues("CVE_NSS", SelectQueryUtil.setValor(contratanteRequest.getNss() ));
		q.agregarParametroValues("NOM_PERSONA", SelectQueryUtil.setValor(contratanteRequest.getNomPersona() ));
		q.agregarParametroValues(ConsultaConstantes.NOM_PRIMER_APELLIDO, SelectQueryUtil.setValor(contratanteRequest.getPrimerApellido() ));
		q.agregarParametroValues(ConsultaConstantes.NOM_SEGUNDO_APELLIDO, SelectQueryUtil.setValor(contratanteRequest.getSegundoApellido() ));
		q.agregarParametroValues("NUM_SEXO", String.valueOf(contratanteRequest.getSexo()));
		q.agregarParametroValues("REF_OTRO_SEXO", SelectQueryUtil.setValor(contratanteRequest.getOtroSexo() ));
		q.agregarParametroValues("FEC_NAC",  SelectQueryUtil.setValor(contratanteRequest.getFecNacimiento() ));
		q.agregarParametroValues("ID_PAIS", String.valueOf(contratanteRequest.getIdPais()));
		q.agregarParametroValues("ID_ESTADO", String.valueOf(contratanteRequest.getIdEstado()));
		q.agregarParametroValues("REF_TELEFONO", SelectQueryUtil.setValor(contratanteRequest.getTelefono() ));
		q.agregarParametroValues("REF_TELEFONO_FIJO", SelectQueryUtil.setValor(contratanteRequest.getTelefonoFijo() ));
		q.agregarParametroValues("REF_CORREO", SelectQueryUtil.setValor(contratanteRequest.getCorreo() ));
		q.agregarParametroValues("TIP_PERSONA", "'"+contratanteRequest.getPersona()+" 67'");
		//q.agregarParametroValues("TIP_PERSONA", SelectQueryUtil.setValor(contratanteRequest.getTipoPersona() ));
		q.agregarParametroValues("NUM_INE", SelectQueryUtil.setValor(contratanteRequest.getIne() ));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ALTA, ConsultaConstantes.CURRENT_DATE);
		log.info(" TERMINO - insertPersona");
		return q.obtenerQueryInsertar();
	}
	
	public String insertDomicilio(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - insertDomicilio");
		final QueryHelper q = new QueryHelper("INSERT INTO SVT_DOMICILIO");
		q.agregarParametroValues("REF_CALLE", SelectQueryUtil.setValor(contratanteRequest.getCp().getDesCalle() ));
		q.agregarParametroValues("NUM_EXTERIOR", SelectQueryUtil.setValor(contratanteRequest.getCp().getNumExterior() ));
		q.agregarParametroValues("NUM_INTERIOR", SelectQueryUtil.setValor(contratanteRequest.getCp().getNumInterior() ));
		q.agregarParametroValues("REF_CP", String.valueOf(contratanteRequest.getCp().getCodigoPostal()));
		q.agregarParametroValues("REF_COLONIA", SelectQueryUtil.setValor(contratanteRequest.getCp().getDesColonia() ));
		q.agregarParametroValues("REF_MUNICIPIO", SelectQueryUtil.setValor(contratanteRequest.getCp().getDesMunicipio()));
		q.agregarParametroValues("REF_ESTADO", SelectQueryUtil.setValor(contratanteRequest.getCp().getDesEstado() ));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ALTA, ConsultaConstantes.CURRENT_DATE);
		log.info(" TERMINO - insertDomicilio");
		return q.obtenerQueryInsertar();
	}
	
	public String insertContratante(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - insertContratante");
		final QueryHelper q = new QueryHelper("INSERT INTO SVC_CONTRATANTE");
		q.agregarParametroValues(ID_PERSONA, 0 < contratanteRequest.getIdPersona()?contratanteRequest.getIdPersona().toString():"idTabla1");
		q.agregarParametroValues(CVE_MATRICULA, SelectQueryUtil.setValor(contratanteRequest.getMatricula() ));
		q.agregarParametroValues(ID_DOMICILIO, 0 < contratanteRequest.getCp().getIdDomicilio()?contratanteRequest.getCp().getIdDomicilio().toString():"idTabla2");
		q.agregarParametroValues(IND_ACTIVO, String.valueOf(1));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ALTA, ConsultaConstantes.CURRENT_DATE);
		log.info(" TERMINO - insertContratante");
		return q.obtenerQueryInsertar();
	}
	
	public String updateContratante(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - updateContratante");
		final QueryHelper q = new QueryHelper("UPDATE SVC_CONTRATANTE");
		q.agregarParametroValues(ID_PERSONA, String.valueOf(contratanteRequest.getIdPersona()));
		q.agregarParametroValues(CVE_MATRICULA, SelectQueryUtil.setValor(contratanteRequest.getMatricula()));
		q.agregarParametroValues(ID_DOMICILIO, String.valueOf(contratanteRequest.getCp().getIdDomicilio()));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_MODIFICA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ACTUALIZACION, ConsultaConstantes.CURRENT_DATE);
		q.addWhere("ID_CONTRATANTE = " + contratanteRequest.getIdContratante());
		log.info(" TERMINO - updateContratante");
		return q.obtenerQueryActualizar();
	}
	
	//aqui esta la falla 
	public String insertTitularBeneficiarios(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - insertTitularBeneficiarios");
		final QueryHelper q = new QueryHelper("INSERT INTO SVT_TITULAR_BENEFICIARIOS");
		q.agregarParametroValues(ID_PERSONA, 0 < contratanteRequest.getIdPersona()?contratanteRequest.getIdPersona().toString():"idTabla1");
		q.agregarParametroValues(CVE_MATRICULA, SelectQueryUtil.setValor(contratanteRequest.getMatricula()));
		q.agregarParametroValues("REF_PERSONA", SelectQueryUtil.setValor(contratanteRequest.getPersona()));
		q.agregarParametroValues(ID_DOMICILIO, 0 < contratanteRequest.getCp().getIdDomicilio()?contratanteRequest.getCp().getIdDomicilio().toString():"idTabla2");
		q.agregarParametroValues(IND_ACTIVO, String.valueOf(1));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ALTA, ConsultaConstantes.CURRENT_DATE);
		log.info(" TERMINO - insertTitularBeneficiarios");
		return q.obtenerQueryInsertar();
	}
	
	public String updateTitularBeneficiarios(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - updateTitularBeneficiarios");
		final QueryHelper q = new QueryHelper("UPDATE SVT_TITULAR_BENEFICIARIOS");
		q.agregarParametroValues(ID_PERSONA, String.valueOf(contratanteRequest.getIdPersona()));
		q.agregarParametroValues(CVE_MATRICULA, SelectQueryUtil.setValor(contratanteRequest.getMatricula()));
		q.agregarParametroValues("REF_PERSONA", SelectQueryUtil.setValor(contratanteRequest.getPersona()));
		q.agregarParametroValues(ID_DOMICILIO, String.valueOf(contratanteRequest.getCp().getIdDomicilio()));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_MODIFICA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ACTUALIZACION, ConsultaConstantes.CURRENT_DATE);
		q.addWhere("ID_TITULAR_BENEFICIARIOS = " + contratanteRequest.getIdTitularBeneficiarios());
		log.info(" TERMINO - updateTitularBeneficiarios");
		return q.obtenerQueryActualizar();
	}
	
	private String updatePersona(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - updatePersona");
		final QueryHelper q = new QueryHelper("UPDATE SVC_PERSONA ");
		q.agregarParametroValues("CVE_RFC", SelectQueryUtil.setValor(contratanteRequest.getRfc() ));
		q.agregarParametroValues("CVE_CURP", SelectQueryUtil.setValor(contratanteRequest.getCurp() ));
		q.agregarParametroValues("CVE_NSS", SelectQueryUtil.setValor(contratanteRequest.getNss() ));
		q.agregarParametroValues("NOM_PERSONA", SelectQueryUtil.setValor(contratanteRequest.getNomPersona() ));
		q.agregarParametroValues(ConsultaConstantes.NOM_PRIMER_APELLIDO, SelectQueryUtil.setValor(contratanteRequest.getPrimerApellido() ));
		q.agregarParametroValues(ConsultaConstantes.NOM_SEGUNDO_APELLIDO, SelectQueryUtil.setValor(contratanteRequest.getSegundoApellido() ));
		q.agregarParametroValues("NUM_SEXO", String.valueOf(contratanteRequest.getSexo()));
		q.agregarParametroValues("REF_OTRO_SEXO", SelectQueryUtil.setValor(contratanteRequest.getOtroSexo() ));
		q.agregarParametroValues("FEC_NAC",  SelectQueryUtil.setValor(contratanteRequest.getFecNacimiento() ));
		q.agregarParametroValues("ID_PAIS", String.valueOf(contratanteRequest.getIdPais()));
		q.agregarParametroValues("ID_ESTADO", String.valueOf(contratanteRequest.getIdEstado()));
		q.agregarParametroValues("REF_TELEFONO", SelectQueryUtil.setValor(contratanteRequest.getTelefono() ));
		q.agregarParametroValues("REF_TELEFONO_FIJO", SelectQueryUtil.setValor(contratanteRequest.getTelefonoFijo() ));
		q.agregarParametroValues("REF_CORREO", SelectQueryUtil.setValor(contratanteRequest.getCorreo() ));
		q.agregarParametroValues("TIP_PERSONA", "'"+ contratanteRequest.getPersona()+" 67A'");
		q.agregarParametroValues("NUM_INE", SelectQueryUtil.setValor(contratanteRequest.getIne() ));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_MODIFICA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ACTUALIZACION, ConsultaConstantes.CURRENT_DATE);
		q.addWhere("ID_PERSONA = " + contratanteRequest.getIdPersona());
		log.info(" TERMINO - updatePersona");
		return q.obtenerQueryActualizar();
	}
	
	private String updateDomicilio(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		final QueryHelper q = new QueryHelper("UPDATE SVT_DOMICILIO ");
		q.agregarParametroValues("REF_CALLE", SelectQueryUtil.setValor(contratanteRequest.getCp().getDesCalle()));
		q.agregarParametroValues("NUM_EXTERIOR", SelectQueryUtil.setValor(contratanteRequest.getCp().getNumExterior()));
		q.agregarParametroValues("NUM_INTERIOR", SelectQueryUtil.setValor(contratanteRequest.getCp().getNumInterior()));
		q.agregarParametroValues("REF_CP", String.valueOf(contratanteRequest.getCp().getCodigoPostal()));
		q.agregarParametroValues("REF_COLONIA", SelectQueryUtil.setValor(contratanteRequest.getCp().getDesColonia()));
		q.agregarParametroValues("REF_MUNICIPIO", SelectQueryUtil.setValor(contratanteRequest.getCp().getDesMunicipio()));
		q.agregarParametroValues("REF_ESTADO", SelectQueryUtil.setValor(contratanteRequest.getCp().getDesEstado()));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ACTUALIZACION, ConsultaConstantes.CURRENT_DATE);
		q.addWhere("ID_DOMICILIO = " + contratanteRequest.getCp().getIdDomicilio());
		return q.obtenerQueryActualizar();
	}
	
	private String insertPlanSfpa(PlanSFPARequest planSFPARequest, UsuarioDto usuarioDto, Map<String, String> map) {
		log.info(" INICIO - insertPlanSfpa");
		//revisar query
		final QueryHelper q = new QueryHelper("INSERT INTO SVT_PLAN_SFPA");
		q.agregarParametroValues("NUM_FOLIO_PLAN_SFPA", planSFPARequest.getNumFolioPlanSfpa());
		q.agregarParametroValues("ID_TIPO_CONTRATACION", String.valueOf( planSFPARequest.getIdTipoContratacion()));
		q.agregarParametroValues(ConsultaConstantes.ID_TITULAR, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_TITULAR).toString()));
		if(map.get(ConsultaConstantes.ID_TITULAR_SUBSTITUTO)!=null) {
			//valor 3 0 4
		q.agregarParametroValues(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_TITULAR_SUBSTITUTO).toString()));
		}
		
		if(map.get(ConsultaConstantes.ID_BENEFICIARIO_1)!=null ) {
			//valor 5
			q.agregarParametroValues(ConsultaConstantes.ID_BENEFICIARIO_1, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_BENEFICIARIO_1).toString()));
		}
			if(map.get(ConsultaConstantes.ID_BENEFICIARIO_2)!=null) {
				//valor 6
				q.agregarParametroValues(ConsultaConstantes.ID_BENEFICIARIO_2, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_BENEFICIARIO_2).toString()));
			
		}
		q.agregarParametroValues("ID_PAQUETE", String.valueOf(planSFPARequest.getIdPaquete()));
		q.agregarParametroValues("IMP_PRECIO", String.valueOf(planSFPARequest.getMonPrecio()));
		q.agregarParametroValues("ID_TIPO_PAGO_MENSUAL", String.valueOf(planSFPARequest.getIdTipoPagoMensual()));
		q.agregarParametroValues("IND_TITULAR_SUBSTITUTO",String.valueOf(planSFPARequest.getIndTitularSubstituto()));
		q.agregarParametroValues("IND_PROMOTOR",String.valueOf(planSFPARequest.getIndPromotor()));
		q.agregarParametroValues("ID_PROMOTOR",String.valueOf(planSFPARequest.getIdPromotor()));
		q.agregarParametroValues("ID_VELATORIO",String.valueOf(usuarioDto.getIdVelatorio()));
		q.agregarParametroValues(ConsultaConstantes.ID_ESTATUS_PLAN_SFPA,String.valueOf(1));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ALTA, ConsultaConstantes.CURRENT_DATE);
		log.info(ConsultaConstantes.QUERY + q.obtenerQueryInsertar());
		log.info(" TERMINO - insertPlanSfpa");
		return q.obtenerQueryInsertar();
	}
	
	private String updatePlanSfpa(PlanSFPARequest planSFPARequest, UsuarioDto usuarioDto, Map<String, String> map) {
		log.info(" INICIO - updatePlanSfpa");
		final QueryHelper q = new QueryHelper("UPDATE SVT_PLAN_SFPA");
		q.agregarParametroValues("NUM_FOLIO_PLAN_SFPA",planSFPARequest.getIndTipoPagoMensual().booleanValue()== true? planSFPARequest.getNumFolioPlanSfpa():SelectQueryUtil.setValor(planSFPARequest.getNumFolioPlanSfpa()));
		q.agregarParametroValues("ID_TIPO_CONTRATACION", String.valueOf( planSFPARequest.getIdTipoContratacion()));
		q.agregarParametroValues(ConsultaConstantes.ID_TITULAR, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_TITULAR).toString()));
		if(map.get(ConsultaConstantes.ID_TITULAR_SUBSTITUTO)!=null) { //se modifico aqui
		q.agregarParametroValues(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_TITULAR_SUBSTITUTO).toString()));
		}
		if(map.get(ConsultaConstantes.ID_BENEFICIARIO_1)!=null) {
			q.agregarParametroValues(ConsultaConstantes.ID_BENEFICIARIO_1, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_BENEFICIARIO_1).toString()));
		}
		if(map.get(ConsultaConstantes.ID_BENEFICIARIO_2)!=null) {
			q.agregarParametroValues(ConsultaConstantes.ID_BENEFICIARIO_2, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_BENEFICIARIO_2).toString()));
		}
		q.agregarParametroValues("ID_PAQUETE", String.valueOf(planSFPARequest.getIdPaquete()));
		q.agregarParametroValues("IMP_PRECIO", String.valueOf(planSFPARequest.getMonPrecio()));
		q.agregarParametroValues("ID_TIPO_PAGO_MENSUAL", String.valueOf(planSFPARequest.getIdTipoPagoMensual()));
		q.agregarParametroValues("IND_TITULAR_SUBSTITUTO",String.valueOf(planSFPARequest.getIndTitularSubstituto()));
		q.agregarParametroValues("IND_MODIF_TITULAR_SUB",String.valueOf(planSFPARequest.getIndModificarTitularSubstituto()));
		q.agregarParametroValues("IND_PROMOTOR",String.valueOf(planSFPARequest.getIndPromotor()));
		q.agregarParametroValues("ID_PROMOTOR",String.valueOf(planSFPARequest.getIdPromotor()));
		q.agregarParametroValues("ID_VELATORIO",String.valueOf(usuarioDto.getIdVelatorio()));
		q.agregarParametroValues(ConsultaConstantes.ID_ESTATUS_PLAN_SFPA,String.valueOf(planSFPARequest.getIdEstatusPlanSfpa()));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_MODIFICA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ACTUALIZACION, ConsultaConstantes.CURRENT_DATE);
		q.addWhere("ID_PLAN_SFPA = " + planSFPARequest.getIdPlanSfpa());
		log.info(ConsultaConstantes.QUERY + q.obtenerQueryInsertar());
		log.info(" TERMINO - updatePlanSfpa");
		return q.obtenerQueryActualizar();
	}
	
	public String updatePlanSfpa(PlanSFPARequest planSFPARequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - updatePlanSfpa");
		final QueryHelper q = new QueryHelper("UPDATE SVT_PLAN_SFPA");
		q.agregarParametroValues(ConsultaConstantes.ID_ESTATUS_PLAN_SFPA,String.valueOf(planSFPARequest.getIdEstatusPlanSfpa()));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_MODIFICA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ACTUALIZACION, ConsultaConstantes.CURRENT_DATE);
		q.agregarParametroValues("ID_USUARIO_BAJA", String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues("FEC_BAJA", ConsultaConstantes.CURRENT_DATE);
		q.addWhere("ID_PLAN_SFPA = " + planSFPARequest.getIdPlanSfpa());
		log.info(ConsultaConstantes.QUERY + q.obtenerQueryInsertar());
		log.info(" TERMINO - updatePlanSfpa");
		return q.obtenerQueryActualizar();
	}
	
	public ArrayList<String> guardarPagoSFPA(PlanSFPARequest planSFPARequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - guardarPagoSFPA");
		ArrayList<String> insertar2 = new ArrayList<>();
		for (int i = 0; i < planSFPARequest.getNumPagoMensual(); i++) {
			//LocalDate fechaActual = LocalDate.now();
			//LocalDate fechafinal = fechaActual.plusMonths(i);
			final QueryHelper q = new QueryHelper("INSERT INTO SVT_PAGO_SFPA");
			q.agregarParametroValues("ID_PLAN_SFPA", ConsultaConstantes.ID_TABLA7);
			q.agregarParametroValues("ID_ESTATUS_PAGO", i == 0?String.valueOf(8):String.valueOf(7));
			q.agregarParametroValues(IND_ACTIVO, String.valueOf(1));
			q.agregarParametroValues("IMP_MONTO_MENSUAL", String.valueOf(planSFPARequest.getMonPrecio()/planSFPARequest.getNumPagoMensual()));
			q.agregarParametroValues("FEC_PARCIALIDAD", "DATE_ADD(CURDATE(), INTERVAL " +i+" MONTH)");
			//q.agregarParametroValues("FEC_PARCIALIDAD", ConsultaConstantes.COMILLA_SIMPLE.concat(fechafinal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).concat(ConsultaConstantes.COMILLA_SIMPLE));
			q.agregarParametroValues(ConsultaConstantes.FEC_ALTA, ConsultaConstantes.CURRENT_DATE);
			q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
			insertar2.add(q.obtenerQueryInsertar());
		}
		log.info(" TERMINO - guardarPagoSFPA");
		return insertar2;
	}
	
	public ArrayList<String> actualizarPagoSFPA(PlanSFPARequest planSFPARequest, PagoFechaResponse pagoFechaResponse,  UsuarioDto usuarioDto) {
		log.info(" INICIO - actualizarPagoSFPA");
		ArrayList<String> insertar2 = new ArrayList<>();
		for (int i = 0; i < planSFPARequest.getNumPagoMensual(); i++) {
			LocalDate fechaAlta = LocalDate.parse(pagoFechaResponse.getFechaAlta(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			LocalDate fechaAnterior = LocalDate.parse(pagoFechaResponse.getFechaParcialidad(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			LocalDate fechafinal = fechaAnterior.plusMonths(i);
			final QueryHelper q = new QueryHelper("INSERT INTO SVT_PAGO_SFPA");
			q.agregarParametroValues("ID_PLAN_SFPA", String.valueOf(planSFPARequest.getIdPlanSfpa()));
			q.agregarParametroValues("ID_ESTATUS_PAGO", i == 0?String.valueOf(8):String.valueOf(7));
			q.agregarParametroValues(IND_ACTIVO, String.valueOf(1));
			q.agregarParametroValues("IMP_MONTO_MENSUAL", String.valueOf(planSFPARequest.getMonPrecio()/planSFPARequest.getNumPagoMensual()));
			q.agregarParametroValues("FEC_PARCIALIDAD", ConsultaConstantes.COMILLA_SIMPLE.concat(fechafinal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).concat(ConsultaConstantes.COMILLA_SIMPLE));
			q.agregarParametroValues(ConsultaConstantes.FEC_ALTA, ConsultaConstantes.COMILLA_SIMPLE.concat(fechaAlta.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).concat(ConsultaConstantes.COMILLA_SIMPLE));
			q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
			q.agregarParametroValues(ConsultaConstantes.FEC_ACTUALIZACION, ConsultaConstantes.CURRENT_DATE);
			q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_MODIFICA, String.valueOf(usuarioDto.getIdUsuario()));
			insertar2.add(q.obtenerQueryInsertar());
		}
		log.info(" TERMINO - actualizarPagoSFPA");
		return insertar2;
	}
	
	private Map<String, String> obtenerContrante(PlanSFPARequest planSFPARequest, ContratanteRequest contratanteRequest, Map<String, String> map) {
		log.info(" INICIO - insertPlanSfpa");
		if (contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR)  && contratanteRequest.getIdContratante() != 0 &&  planSFPARequest.getIndTitularSubstituto() == 1) {
			map.put(ConsultaConstantes.ID_TITULAR,  String.valueOf(contratanteRequest.getIdContratante()));
			map.put(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, null);
		}  else if (contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR)  && contratanteRequest.getIdContratante() != 0 &&  planSFPARequest.getIndTitularSubstituto() == 0) {
			map.put(ConsultaConstantes.ID_TITULAR,  String.valueOf(contratanteRequest.getIdContratante()));
		} else if (contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR)  && contratanteRequest.getIdContratante() == 0 &&  planSFPARequest.getIndTitularSubstituto() == 0) {
			map.put(ConsultaConstantes.ID_TITULAR,  String.valueOf(ConsultaConstantes.ID_TABLA3));
		} else if(contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR)  && contratanteRequest.getIdContratante() == 0 &&  planSFPARequest.getIndTitularSubstituto() == 1 ) {
			map.put(ConsultaConstantes.ID_TITULAR, ConsultaConstantes.ID_TABLA3);
			map.put(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, null);
		} else if(contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR_SUBSTITUTO)  && contratanteRequest.getIdTitularBeneficiarios() != 0 &&  planSFPARequest.getIndTitularSubstituto() == 0 ) {
			map.put(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, String.valueOf(contratanteRequest.getIdTitularBeneficiarios()));
		} else if (contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR_SUBSTITUTO)  && contratanteRequest.getIdTitularBeneficiarios() == 0 &&  planSFPARequest.getIndTitularSubstituto() == 0 ) {
			map.put(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, ConsultaConstantes.ID_TABLA4);
		} else if (contratanteRequest.getPersona().equals(ConsultaConstantes.BENEFICIARIO_1)  && contratanteRequest.getIdTitularBeneficiarios() != 0) {
			map.put(ConsultaConstantes.ID_BENEFICIARIO_1, String.valueOf(contratanteRequest.getIdTitularBeneficiarios()));
		}else if (contratanteRequest.getPersona().equals(ConsultaConstantes.BENEFICIARIO_1)  && contratanteRequest.getIdTitularBeneficiarios() == 0) {
			map.put(ConsultaConstantes.ID_BENEFICIARIO_1, ConsultaConstantes.ID_TABLA5);
		//se manda el beneficiarios a el mapa datos
		}else if (contratanteRequest.getPersona().equals(ConsultaConstantes.BENEFICIARIO_2)  && contratanteRequest.getIdTitularBeneficiarios() != 0) {
			map.put(ConsultaConstantes.ID_BENEFICIARIO_2, String.valueOf(contratanteRequest.getIdTitularBeneficiarios()));
		}else if (contratanteRequest.getPersona().equals(ConsultaConstantes.BENEFICIARIO_2)  && contratanteRequest.getIdTitularBeneficiarios() == 0 ) {
			map.put(ConsultaConstantes.ID_BENEFICIARIO_2, ConsultaConstantes.ID_TABLA6);
		}
		return map;
	}
}
