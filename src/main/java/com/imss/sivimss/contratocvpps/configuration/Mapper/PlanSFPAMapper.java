package com.imss.sivimss.contratocvpps.configuration.Mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.contratocvpps.model.request.PagosSFPA;
import com.imss.sivimss.contratocvpps.model.request.PlanSFPA;

public interface PlanSFPAMapper {

	@Insert(value = "INSERT INTO  SVT_PLAN_SFPA  ( " +
			" NUM_FOLIO_PLAN_SFPA , " +
			" ID_TIPO_CONTRATACION , " +
			" ID_TITULAR ," +
			" ID_PAQUETE , " +
			" IMP_PRECIO ," +
			" ID_TIPO_PAGO_MENSUAL ," +
			" IND_TITULAR_SUBSTITUTO ," +
			" IND_MODIF_TITULAR_SUB , " +
			" ID_TITULAR_SUBSTITUTO , " +
			" ID_BENEFICIARIO_1 ," +
			" ID_BENEFICIARIO_2 ," +
			" IND_PROMOTOR , " +
			" ID_PROMOTOR , " +
			" FEC_INGRESO , " +
			" TIM_HORA_INGRESO , " +
			" ID_VELATORIO , " +
			" ID_ESTATUS_PLAN_SFPA ," +
			" IND_ACTIVO ," +
			" ID_USUARIO_ALTA ," +
			" FEC_ALTA ) values (" +

			" (SELECT CONCAT_WS('-', " +
			"  (SELECT SUBSTRING(UPPER(SV.DES_VELATORIO), 1, 3)" +
			"   FROM SVC_VELATORIO SV" +
			"   WHERE SV.ID_VELATORIO = 1 )," +
			"  (SELECT SUBSTRING(UPPER(SP.REF_PAQUETE_NOMBRE), 1, 3)" +
			"   FROM SVT_PAQUETE SP" +
			"   WHERE SP.ID_PAQUETE = 1 )," +
			"  (SELECT STPM.DES_TIPO_PAGO_MENSUAL" +
			"   FROM SVC_TIPO_PAGO_MENSUAL STPM" +
			"   WHERE STPM.ID_TIPO_PAGO_MENSUAL = 2 )," +
			"  (SELECT IFNULL(MAX(SPSFPA.ID_PLAN_SFPA), 0) + 1" +
			"   FROM SVT_PLAN_SFPA SPSFPA))" +
			"  FROM DUAL)" +
			" 1," +
			" #{datos.idTitular}, " +
			" #{datos.idPaquete}, " +
			" (SELECT MON_PRECIO FROM SVT_PAQUETE WHERE ID_PAQUETE = #{datos.idPaquete})," +
			" #{datos.idTipoPagoMensual}," +
			" #{datos.indTitularSubstituto}," +
			" #{datos.indModificarTitularSubstituto}," +
			" #{datos.indTitularSubstituto}," +
			" #{datos.idBeneficiario1}," +
			" #{datos.idBeneficiario2}," +
			" #{datos.indPromotor}," +
			" #{datos.idPromotor}," +
			" CURDATE()," +
			" (SELECT TIME(CURRENT_TIME()))," +
			" #{datos.idVelatorio}," +
			" 1," +
			" 1," +
			" ${idUsuario}," +
			" CURRENT_TIMESTAMP())")
	@Options(useGeneratedKeys = true, keyProperty = "datos.idPlanSfpa", keyColumn = "ID_PLAN_SFPA")
	public int agregarConvenioPF(@Param("datos") PlanSFPA datos);

	@Insert(value = "INSERT INTO SVC_PERSONA  " +
			"( " +
			"CVE_RFC," +
			"CVE_CURP, " +
			"NOM_PERSONA," +
			"NOM_PRIMER_APELLIDO," +
			"NOM_SEGUNDO_APELLIDO," +
			"FEC_NAC, " +
			"ID_PAIS, " +
			"REF_TELEFONO, " +
			"REF_TELEFONO_FIJO," +
			"REF_CORREO, " +
			"ID_USUARIO_ALTA," +
			" NUM_SEXO , " +
			" REF_OTRO_SEXO,  " +
			"FEC_ALTA) " +
			" VALUES " +
			"( " +
			"#{datos.rfc}," +
			"#{datos.curp}, " +
			"#{datos.nomPersona}, " +
			"#{datos.primerApellido}, " +
			"#{datos.segundoApellido}, " +
			"#{datos.fecNacimiento}, " +
			"#{datos.idPais}, " +
			"#{datos.telefono}, " +
			"#{datos.telefonoFijo}, " +
			"#{datos.correo}, " +
			"#{datos.idUsuario}, " +
			"#{datos.idSexo} , " +
			"#{datos.otroSexo} ," +
			" CURRENT_TIMESTAMP())")
	@Options(useGeneratedKeys = true, keyProperty = "datos.idPersona", keyColumn = "ID_PERSONA")
	public int agregarPersona(@Param("datos") PlanSFPA persona);

	@Insert(value = "INSERT INTO SVT_DOMICILIO " +
			" (" +
			" REF_CALLE, " +
			" NUM_EXTERIOR, " +
			" NUM_INTERIOR, " +
			" REF_CP,  " +
			" REF_COLONIA, " +
			" REF_MUNICIPIO,  " +
			" REF_ESTADO,  " +
			" ID_USUARIO_ALTA,  " +
			" FEC_ALTA)  " +
			" VALUES  " +
			" ( " +
			" #{datos.calle}, " +
			" #{datos.noExterior}, " +
			" #{datos.noInterior}, " +
			" #{datos.cp}, " +
			" #{datos.colonia}, " +
			" #{datos.municipio}, " +
			" #{datos.estado}, " +
			" #{datos.idUsuario}, " +
			" CURRENT_TIMESTAMP() " +
			" )  ")
	@Options(useGeneratedKeys = true, keyProperty = "datos.idDomicilio", keyColumn = "ID_DOMICILIO")
	public int agregarDomicilio(@Param("datos") PlanSFPA datos);

	@Insert(value = "INSERT INTO  SVT_TITULAR_BENEFICIARIOS  " +
			"(" +
			" CVE_MATRICULA , " +
			" REF_PERSONA ," +
			" ID_PERSONA ," +
			" ID_DOMICILIO ," +
			" IND_ACTIVO , " +
			" FEC_ALTA , " +
			" ID_USUARIO_ALTA  ) " +
			" VALUES  " +
			" ( " +
			" #{datos.matricula}, " +
			" #{datos.persona}, " +
			" #{datos.idPersona}, " +
			" #{datos.idDomicilio}, " +
			" 1, " +
			"  CURRENT_TIMESTAMP() , " +
			" #{datos.idUsuario},  " +
			" )  ")
	@Options(useGeneratedKeys = true, keyProperty = "datos.idTitularBeneficiario", keyColumn = "ID_TITULAR_BENEFICIARIOS")
	public int agregarTitulaBeneficiario(@Param("datos") PlanSFPA datos);

	@Insert(value = "INSERT INTO  SVT_PAGO_SFPA  (" +
			" ID_PLAN_SFPA , " +
			" IMP_MONTO_MENSUAL ," +
			" FEC_PARCIALIDAD , " +
			" IND_ACTIVO , " +
			" ID_USUARIO_ALTA ," +
			" FEC_ALTA ,  " +
			" ID_ESTATUS_PAGO " +
			")" +
			" VALUES  " +
			" ( " +
			" #{datos.idPlanSfpa}, " +
			" #{datos.montoParcialidad}, " +
			" #{datos.fechaParcialidad}, " +

			" 1, " +
			" #{datos.idUsuario},  " +
			"  CURRENT_TIMESTAMP() , " +
			"7" +
			" )  ")
	@Options(useGeneratedKeys = true, keyProperty = "datos.idTitularBeneficiario", keyColumn = "ID_PAGO_SFPA")
	public int agregarParcialidades(@Param("datos") PagosSFPA datos);

	@Select(value = "SELECT DATE_ADD(CURRENT_DATE(), INTERVAL #{in.datos}  MONTH) as fechaParcialidad from dual ")
	public Map<String, Object> fechasMensualidades(@Param("datos") PagosSFPA datos);

}
