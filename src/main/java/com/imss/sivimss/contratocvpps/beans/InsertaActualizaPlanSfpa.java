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
import com.imss.sivimss.contratocvpps.util.ConsultaConstantes;
import com.imss.sivimss.contratocvpps.util.QueryHelper;
import com.imss.sivimss.contratocvpps.util.SelectQueryUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InsertaActualizaPlanSfpa  implements Serializable {
	
	private static final String ID_DOMICILIO = "ID_DOMICILIO";
	private static final String ID_PERSONA = "ID_PERSONA";
	private static final long serialVersionUID = 1L;
	
	public InsertPlanSfpaRequest insertaPlanSfpa(PlanSFPARequest planSFPARequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - insertaPlanSfpa");
		String query = "";
		Map<String, String> map = new HashMap<>();
		InsertPlanSfpaRequest insertPlanSfpaRequest = new InsertPlanSfpaRequest();
		ArrayList<String> insertar = new ArrayList<>();
		ArrayList<String> actualizar = new ArrayList<>();
		if (planSFPARequest.getIdPlanSfpa()!= null) {
			for(ContratanteRequest contratanteRequest: planSFPARequest.getTitularesBeneficiarios()) {
				log.info("  idPersona:  " + contratanteRequest.getIdPersona() +"  idContratante:  "+  contratanteRequest.getIdContratante() + "  IdDomicilio:  "+ contratanteRequest.getCp().getIdDomicilio());
				if(contratanteRequest.getPersona().equalsIgnoreCase(ConsultaConstantes.TITULAR)) {
					if(contratanteRequest.getIdPersona() != null && contratanteRequest.getCp().getIdDomicilio() != null) {
						actualizar.add(updateContratante(contratanteRequest, usuarioDto));
						insertar.add(updatePersona(contratanteRequest, usuarioDto));
						insertar.add(updateDomicilio(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertPersona(contratanteRequest, usuarioDto));
						insertar.add(insertDomicilio(contratanteRequest, usuarioDto));
					}
					if(contratanteRequest.getIdContratante() != null) {
						actualizar.add(updateContratante(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertContratante(contratanteRequest, usuarioDto));
					}
				} else {
					if(contratanteRequest.getIdPersona() != null && contratanteRequest.getCp().getIdDomicilio() != null) {
						insertar.add(updatePersona(contratanteRequest, usuarioDto));
						insertar.add(updateDomicilio(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertPersona(contratanteRequest, usuarioDto));
						insertar.add(insertDomicilio(contratanteRequest, usuarioDto));
					}
					if (contratanteRequest.getIdTitularBeneficiarios() != null) {
						actualizar.add(updateTitularBeneficiarios(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertTitularBeneficiarios(contratanteRequest, usuarioDto));
					}
				}
				map =  obtenerContrante(planSFPARequest, contratanteRequest, map);
			}
			query = updatePlanSfpa(planSFPARequest, usuarioDto, map);
			actualizar.add(query);
		} else  {
			for(ContratanteRequest contratanteRequest: planSFPARequest.getTitularesBeneficiarios()) {
				log.info("  idPersona:  " + contratanteRequest.getIdPersona() +"  idContratante:  "+  contratanteRequest.getIdContratante() + "  IdDomicilio:  "+ contratanteRequest.getCp().getIdDomicilio());
				if(contratanteRequest.getPersona().equalsIgnoreCase(ConsultaConstantes.TITULAR)) {
					if(contratanteRequest.getIdPersona() != null && contratanteRequest.getCp().getIdDomicilio() != null) {
						actualizar.add(updatePersona(contratanteRequest, usuarioDto));
						actualizar.add(updateDomicilio(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertPersona(contratanteRequest, usuarioDto));
						insertar.add(insertDomicilio(contratanteRequest, usuarioDto));
					}
					if(contratanteRequest.getIdContratante() != null) {
						actualizar.add(updateContratante(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertContratante(contratanteRequest, usuarioDto));
					}
				} else {
					if(contratanteRequest.getIdPersona() != null && contratanteRequest.getCp().getIdDomicilio() != null) {	
						actualizar.add(updatePersona(contratanteRequest, usuarioDto));
						actualizar.add(updateDomicilio(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertPersona(contratanteRequest, usuarioDto));
						insertar.add(insertDomicilio(contratanteRequest, usuarioDto));
					}
					if (contratanteRequest.getIdTitularBeneficiarios() != null) {
						actualizar.add(updateTitularBeneficiarios(contratanteRequest, usuarioDto));
					} else {
						insertar.add(insertTitularBeneficiarios(contratanteRequest, usuarioDto));
					}
				}

				map = obtenerContrante(planSFPARequest, contratanteRequest, map);
			}
			
			insertPlanSfpaRequest.setInsertar2(guardarPagoSFPA(planSFPARequest, usuarioDto));
			
			query = insertPlanSfpa(planSFPARequest, usuarioDto, map);
			insertar.add(query);
		}
		
		insertPlanSfpaRequest.setInsertar(insertar);
		insertPlanSfpaRequest.setActualizar(actualizar);
	
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
		q.agregarParametroValues("TIP_PERSONA", SelectQueryUtil.setValor(contratanteRequest.getTipoPersona() ));
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
		q.agregarParametroValues(ID_PERSONA, "idTabla1");
		q.agregarParametroValues("CVE_MATRICULA", SelectQueryUtil.setValor(contratanteRequest.getMatricula() ));
		q.agregarParametroValues(ID_DOMICILIO, "idTabla2");
		q.agregarParametroValues("IND_ACTIVO", String.valueOf(1));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ALTA, ConsultaConstantes.CURRENT_DATE);
		log.info(" TERMINO - insertContratante");
		return q.obtenerQueryInsertar();
	}
	
	public String updateContratante(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - updateContratante");
		final QueryHelper q = new QueryHelper("UPDATE SVC_CONTRATANTE");
		q.agregarParametroValues(ID_PERSONA, String.valueOf(contratanteRequest.getIdPersona()));
		q.agregarParametroValues("CVE_MATRICULA", SelectQueryUtil.setValor(contratanteRequest.getMatricula() ));
		q.agregarParametroValues(ID_DOMICILIO, String.valueOf(contratanteRequest.getCp().getIdDomicilio()));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_MODIFICA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ACTUALIZACION, ConsultaConstantes.CURRENT_DATE);
		q.addWhere("ID_CONTRATANTE = " + contratanteRequest.getIdContratante());
		log.info(" TERMINO - updateContratante");
		return q.obtenerQueryActualizar();
	}
	
	public String insertTitularBeneficiarios(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - insertTitularBeneficiarios");
		final QueryHelper q = new QueryHelper("INSERT INTO SVT_TITULAR_BENEFICIARIOS");
		q.agregarParametroValues(ID_PERSONA, "idTabla1");
		q.agregarParametroValues("REF_PERSONA", SelectQueryUtil.setValor(contratanteRequest.getPersona()));
		q.agregarParametroValues(ID_DOMICILIO, "idTabla2");
		q.agregarParametroValues("IND_ACTIVO", String.valueOf(1));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ALTA, ConsultaConstantes.CURRENT_DATE);
		log.info(" TERMINO - insertTitularBeneficiarios");
		return q.obtenerQueryInsertar();
	}
	
	public String updateTitularBeneficiarios(ContratanteRequest contratanteRequest, UsuarioDto usuarioDto) {
		log.info(" INICIO - updateTitularBeneficiarios");
		final QueryHelper q = new QueryHelper("UPDATE SVT_TITULAR_BENEFICIARIOS");
		q.agregarParametroValues(ID_PERSONA, String.valueOf(contratanteRequest.getIdPersona()));
		q.agregarParametroValues("REF_PERSONA", SelectQueryUtil.setValor(contratanteRequest.getPersona()));
		q.agregarParametroValues(ID_DOMICILIO, String.valueOf(contratanteRequest.getCp().getIdDomicilio()));
		q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_MODIFICA, String.valueOf(usuarioDto.getIdUsuario()));
		q.agregarParametroValues(ConsultaConstantes.FEC_ACTUALIZACION, ConsultaConstantes.CURRENT_DATE);
		q.addWhere("ID_CONTRATANTE = " + contratanteRequest.getIdContratante());
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
		q.agregarParametroValues("TIP_PERSONA", SelectQueryUtil.setValor(contratanteRequest.getTipoPersona() ));
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
		final QueryHelper q = new QueryHelper("INSERT INTO SVT_PLAN_SFPA");
		q.agregarParametroValues("NUM_FOLIO_PLAN_SFPA", planSFPARequest.getNumFolioPlanSfpa());
		q.agregarParametroValues("ID_TIPO_CONTRATACION", String.valueOf( planSFPARequest.getIdTipoContratacion()));
		q.agregarParametroValues(ConsultaConstantes.ID_TITULAR, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_TITULAR).toString()));
		q.agregarParametroValues(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_TITULAR_SUBSTITUTO).toString()));
		if(planSFPARequest.getTitularesBeneficiarios().size() == 3) {
			q.agregarParametroValues(ConsultaConstantes.ID_BENEFICIARIO_1, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_BENEFICIARIO_1).toString()));
		}
		if(planSFPARequest.getTitularesBeneficiarios().size() == 4) {
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
		q.agregarParametroValues("NUM_FOLIO_PLAN_SFPA", planSFPARequest.getNumFolioPlanSfpa());
		q.agregarParametroValues("ID_TIPO_CONTRATACION", String.valueOf( planSFPARequest.getIdTipoContratacion()));
		q.agregarParametroValues(ConsultaConstantes.ID_TITULAR, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_TITULAR).toString()));
		q.agregarParametroValues(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_TITULAR_SUBSTITUTO).toString()));
		if(planSFPARequest.getTitularesBeneficiarios().size() == 3) {
			q.agregarParametroValues(ConsultaConstantes.ID_BENEFICIARIO_1, SelectQueryUtil.setValor( map.get(ConsultaConstantes.ID_BENEFICIARIO_1).toString()));
		}
		if(planSFPARequest.getTitularesBeneficiarios().size() == 4) {
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
			LocalDate fechaActual = LocalDate.now();
			LocalDate fechafinal = fechaActual.plusMonths(i);
			final QueryHelper q = new QueryHelper("INSERT INTO SVC_PAGO_SFPA");
			q.agregarParametroValues("ID_PLAN_SFPA", ConsultaConstantes.ID_TABLA7);
			q.agregarParametroValues("ID_ESTATUS_PAGO", i == 0?String.valueOf(8):String.valueOf(7));
			q.agregarParametroValues("IND_ACTIVO", String.valueOf(1));
			q.agregarParametroValues("IMP_MONTO_MENSUAL", String.valueOf(planSFPARequest.getMonPrecio()/planSFPARequest.getNumPagoMensual()));
			q.agregarParametroValues("FEC_PARCIALIDAD",fechafinal.format(DateTimeFormatter.ofPattern("d/MM/uuuu")));
			q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
			q.agregarParametroValues(ConsultaConstantes.FEC_ALTA, ConsultaConstantes.CURRENT_DATE);
			q.agregarParametroValues(ConsultaConstantes.ID_USUARIO_ALTA, String.valueOf(usuarioDto.getIdUsuario()));
			insertar2.add(q.obtenerQueryInsertar());
		}
		log.info(" TERMINO - guardarPagoSFPA");
		return insertar2;
	}
	
	private Map<String, String> obtenerContrante(PlanSFPARequest planSFPARequest, ContratanteRequest contratanteRequest, Map<String, String> map) {
		log.info(" INICIO - insertPlanSfpa");
		if (contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR)  && contratanteRequest.getIdContratante() != null &&  planSFPARequest.getIndTitularSubstituto() == 1) {
			map.put(ConsultaConstantes.ID_TITULAR,  String.valueOf(contratanteRequest.getIdContratante()));
			map.put(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, String.valueOf(contratanteRequest.getIdContratante()));
		}  else if (contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR)  && contratanteRequest.getIdContratante() != null &&  planSFPARequest.getIndTitularSubstituto() == 0) {
			map.put(ConsultaConstantes.ID_TITULAR,  String.valueOf(contratanteRequest.getIdContratante()));
		} else if (contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR)  && contratanteRequest.getIdContratante() == null &&  planSFPARequest.getIndTitularSubstituto() == 0) {
			map.put(ConsultaConstantes.ID_TITULAR,  String.valueOf(ConsultaConstantes.ID_TABLA3));
		} else if(contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR)  && contratanteRequest.getIdContratante() == null &&  planSFPARequest.getIndTitularSubstituto() == 1 ) {
			map.put(ConsultaConstantes.ID_TITULAR, ConsultaConstantes.ID_TABLA3);
			map.put(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, ConsultaConstantes.ID_TABLA3);
		} else if(contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR_SUBSTITUTO)  && contratanteRequest.getIdContratante() != null &&  planSFPARequest.getIndTitularSubstituto() == 0 ) {
			map.put(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, String.valueOf(contratanteRequest.getIdContratante()));
		} else if (contratanteRequest.getPersona().equals(ConsultaConstantes.TITULAR_SUBSTITUTO)  && contratanteRequest.getIdContratante() == null &&  planSFPARequest.getIndTitularSubstituto() == 0 ) {
			map.put(ConsultaConstantes.ID_TITULAR_SUBSTITUTO, "idTabla4");
		} else if (contratanteRequest.getPersona().equals(ConsultaConstantes.BENEFICIARIO_1)  && contratanteRequest.getIdContratante() != null) {
			map.put(ConsultaConstantes.ID_BENEFICIARIO_1, String.valueOf(contratanteRequest.getIdContratante()));
		}else if (contratanteRequest.getPersona().equals(ConsultaConstantes.BENEFICIARIO_1)  && contratanteRequest.getIdContratante() == null) {
			map.put(ConsultaConstantes.ID_BENEFICIARIO_1, "idTabla5");
		}else if (contratanteRequest.getPersona().equals(ConsultaConstantes.BENEFICIARIO_2)  && contratanteRequest.getIdContratante() != null) {
			map.put(ConsultaConstantes.ID_BENEFICIARIO_2, String.valueOf(contratanteRequest.getIdContratante()));
		}else if (contratanteRequest.getPersona().equals(ConsultaConstantes.BENEFICIARIO_2)  && contratanteRequest.getIdContratante() == null ) {
			map.put(ConsultaConstantes.ID_BENEFICIARIO_2, "idTabla6");
		}
		return map;
	}
}
