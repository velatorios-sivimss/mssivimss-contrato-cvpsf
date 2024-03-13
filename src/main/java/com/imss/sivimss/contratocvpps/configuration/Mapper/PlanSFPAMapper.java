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
			"  FROM DUAL)," +
			" 1," +
			" #{datos.idTitular}, " +
			" #{datos.idPaquete}, " +
			" (SELECT MON_PRECIO FROM SVT_PAQUETE WHERE ID_PAQUETE = #{datos.idPaquete})," +
			" #{datos.idTipoPagoMensual}," +
			" #{datos.indTitularSubstituto}," +
			" #{datos.indModificarTitularSubstituto}," +
			" #{datos.idTitularSubstituto}," +
			" #{datos.idBeneficiario1}," +
			" #{datos.idBeneficiario2}," +
			" #{datos.indPromotor}," +
			" #{datos.idPromotor}," +
			" CURDATE()," +
			" (SELECT TIME(CURRENT_TIME()))," +
			" #{datos.idVelatorio}," +
			" 1," +
			" 1," +
			" ${datos.idUsuario}," +
			" CURRENT_TIMESTAMP())")
	@Options(useGeneratedKeys = true, keyProperty = "datos.idPlanSfpa", keyColumn = "ID_PLAN_SFPA")
	public int agregarContratoPFPA(@Param("datos") PlanSFPA datos);

	@Insert(value = "INSERT INTO SVC_PERSONA  " +
			"( " +
			"CVE_RFC," +
			"CVE_CURP, " +
			"CVE_NSS, " +
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
			"#{datos.nss}, " +
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
			" #{datos.desCalle}, " +
			" #{datos.numExterior}, " +
			" #{datos.numInterior}, " +
			" #{datos.codigoPostal}, " +
			" #{datos.desColonia}, " +
			" #{datos.desMunicipio}, " +
			" #{datos.desEstado}, " +
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
			" #{datos.idUsuario}  " +
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
			" (SELECT DATE_ADD(CURRENT_DATE(), INTERVAL #{datos.noMes}  MONTH) ), " +
			" 1, " +
			" #{datos.idUsuario},  " +
			"  CURRENT_TIMESTAMP() , " +
			" #{datos.idEstatusPago} " +
			" )  ")
	@Options(useGeneratedKeys = true, keyProperty = "datos.idPagoSFPA", keyColumn = "ID_PAGO_SFPA")
	public int agregarParcialidades(@Param("datos") PagosSFPA datos);

	@Insert(value = "INSERT INTO SVC_CONTRATANTE  " +
			" (  " +
			" ID_PERSONA, " +
			" CVE_MATRICULA, " +
			" ID_DOMICILIO, " +
			" FEC_ALTA, " +
			" ID_USUARIO_ALTA, " +
			" IND_ACTIVO) " +
			" VALUES ( " +
			" #{datos.idPersona}, " +
			" #{datos.matricula}, " +
			" #{datos.idDomicilio}, " +
			" CURRENT_TIMESTAMP(), " +
			" #{datos.idUsuario}, " +
			" 1 " +
			" )  ")
	@Options(useGeneratedKeys = true, keyProperty = "datos.idContratante", keyColumn = "ID_CONTRATANTE")
	public int agregarContratante(@Param("datos") PlanSFPA datos);

	@Update(value = ""
			+ "UPDATE SVT_DOMICILIO  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_TIMESTAMP(), "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ," +
			" REF_CALLE = #{in.desCalle} , " +
			" NUM_EXTERIOR= #{in.numExterior} , " +
			" NUM_INTERIOR = #{in.numInterior} , " +
			" REF_CP = #{in.codigoPostal} ,  " +
			" REF_COLONIA = #{in.desColonia} , " +
			" REF_MUNICIPIO = #{in.desMunicipio} ,  " +
			" REF_ESTADO = #{in.desEstado}   "
			+ " WHERE ID_DOMICILIO = #{in.idDomicilio} ")
	public int updateDomicilio(@Param("in") PlanSFPA persona);

	@Update(value = ""
			+ "UPDATE SVC_PERSONA  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_TIMESTAMP(), "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ," +
			" CVE_RFC = #{in.rfc} , " +
			" CVE_CURP= #{in.curp} , " +
			" CVE_NSS = #{in.nss} , " +
			" NOM_PERSONA = #{in.nomPersona} ,  " +
			" NOM_PRIMER_APELLIDO = #{in.primerApellido} , " +
			" NOM_SEGUNDO_APELLIDO = #{in.segundoApellido} ,  " +
			" NUM_SEXO = #{in.idSexo} ,   " +
			" REF_OTRO_SEXO = #{in.otroSexo} , " +
			" FEC_NAC= #{in.fecNacimiento} , " +
			" ID_PAIS = #{in.idPais} , " +
			" ID_ESTADO = #{in.idEstado} ,  " +
			" REF_TELEFONO = #{in.telefono} , " +
			" REF_TELEFONO_FIJO = #{in.telefonoFijo} ,  " +
			" REF_CORREO = #{in.correo}   " +
			" WHERE ID_PERSONA = #{in.idPersona} ")
	public int updatePersona(@Param("in") PlanSFPA persona);

	@Update(value = ""
			+ "UPDATE SVC_CONTRATANTE  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_TIMESTAMP(), "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ," +
			" CVE_MATRICULA = #{in.matricula},  " +
			" ID_DOMICILIO = #{in.idDomicilio}  " +
			" WHERE ID_CONTRATANTE = #{in.idContratante} " +
			" AND ID_PERSONA = #{in.idPersona}  ")
	public int updateContratante(@Param("in") PlanSFPA persona);

	@Select(value = "SELECT " +
			" SPSA.ID_PLAN_SFPA AS idPlanSfpa, " +
			" SPSA.NUM_FOLIO_PLAN_SFPA AS numFolio, " +
			" SPC.ID_PERSONA AS idPersona, " +
			" SC.ID_CONTRATANTE AS idContratante, " +
			" IFNULL(SC.CVE_MATRICULA, '') AS matricula, " +
			" IFNULL(SPC.CVE_CURP, '') AS curp, " +
			" IFNULL(SPC.CVE_NSS, '') AS nss, " +
			" IFNULL(SPC.CVE_RFC, '') AS rfc, " +
			" IFNULL(SPC.NOM_PERSONA, '') AS nomPersona, " +
			" IFNULL(SPC.NOM_PRIMER_APELLIDO, '') AS primerApellido, " +
			" IFNULL(SPC.NOM_SEGUNDO_APELLIDO, '') AS segundoApellido, " +
			" IFNULL(SPC.NUM_SEXO, 0) AS idSexo, " +
			" IFNULL(SPC.REF_OTRO_SEXO, '') AS otroSexo, " +

			" CASE WHEN SPC.NUM_SEXO = 1 THEN 'MUJER' WHEN SPC.NUM_SEXO = 2 THEN 'HOMBRE' ELSE IFNULL(SPC.REF_OTRO_SEXO, '') END AS sexo, "
			+
			" SPC.FEC_NAC AS fechaNac, " +
			" CASE WHEN SPC.ID_PAIS = NULL " +
			" OR SPC.ID_PAIS = 119 THEN 1 ELSE 2 END AS idNacionalidad, " +
			" CASE WHEN SPC.ID_PAIS = NULL " +
			" OR SPC.ID_PAIS = 119 THEN 'MEXICANA' ELSE 'EXTRANJERA' END AS nacionalidad, " +
			" IFNULL(SPC.ID_PAIS, 119) AS idPais, " +
			" IFNULL(SPC.ID_ESTADO,0) AS idEstado, " +
			" IFNULL(SC.ID_DOMICILIO, 0) AS idDomicilio, " +
			" IFNULL(SVD.REF_CALLE, '') AS desCalle, " +
			" IFNULL(SVD.NUM_EXTERIOR, '') AS numExterior, " +
			" IFNULL(SVD.NUM_INTERIOR, '') AS numInterior, " +
			" IFNULL(SVD.REF_CP, '') AS codigoPostal, " +
			" IFNULL(SVD.REF_COLONIA, '') AS desColonia, " +
			" IFNULL(SVD.REF_MUNICIPIO, '') AS desMunicipio, " +
			" IFNULL(SVD.REF_ESTADO, '') AS desEstado, " +
			" IFNULL(SPC.REF_CORREO, '') AS correo, " +
			" IFNULL(REF_TELEFONO,'') as telefono," +
			" ifnull(REF_TELEFONO_FIJO,'') as telefonoFijo," +
			" CONCAT_WS( " +
			"  ' ', SPC.NOM_PERSONA, SPC.NOM_PRIMER_APELLIDO, " +
			"  SPC.NOM_SEGUNDO_APELLIDO " +
			" ) AS nombreCompleto " +
			" FROM " +
			" SVT_PLAN_SFPA SPSA " +
			" JOIN SVC_VELATORIO SV ON SPSA.ID_VELATORIO = SV.ID_VELATORIO " +
			" JOIN SVC_CONTRATANTE SC ON SPSA.ID_TITULAR = SC.ID_CONTRATANTE " +
			" JOIN SVC_PERSONA SPC ON SC.ID_PERSONA = SPC.ID_PERSONA " +
			" JOIN SVT_DOMICILIO SVD ON SC.ID_DOMICILIO = SVD.ID_DOMICILIO " +
			"WHERE " +
			" SPSA.ID_PLAN_SFPA = #{idPlan}")
	public Map<String, Object> datosContratante(@Param("idPlan") Integer datos);

	@Select(value = "SELECT " +
			" SPSA.ID_PLAN_SFPA AS idPlanSfpa, " +
			" SPC.ID_PERSONA AS idPersona, " +
			" TB.ID_TITULAR_BENEFICIARIOS AS idTitularBeneficiario, " +
			" IFNULL(TB.CVE_MATRICULA, '') AS matricula, " +
			" IFNULL(SPC.CVE_CURP, '') AS curp, " +
			" IFNULL(SPC.CVE_NSS, '') AS nss, " +
			" IFNULL(SPC.CVE_RFC, '') AS rfc, " +
			" IFNULL(SPC.NOM_PERSONA, '') AS nomPersona, " +
			" IFNULL(SPC.NOM_PRIMER_APELLIDO, '') AS primerApellido, " +
			" IFNULL(SPC.NOM_SEGUNDO_APELLIDO, '') AS segundoApellido, " +
			" IFNULL(SPC.NUM_SEXO, 0) AS idSexo, " +
			" IFNULL(SPC.REF_OTRO_SEXO, '') AS otroSexo, " +
			" CASE WHEN SPC.NUM_SEXO = 1 THEN 'MUJER' WHEN SPC.NUM_SEXO = 2 THEN 'HOMBRE' ELSE IFNULL(SPC.REF_OTRO_SEXO, '') END AS sexo, "
			+
			" SPC.FEC_NAC AS fechaNac, " +
			" CASE WHEN SPC.ID_PAIS = NULL " +
			" OR SPC.ID_PAIS = 119 THEN 1 ELSE 2 END AS idNacionalidad, " +
			" CASE WHEN SPC.ID_PAIS = NULL " +
			" OR SPC.ID_PAIS = 119 THEN 'MEXICANA' ELSE 'EXTRANJERA' END AS nacionalidad, " +
			" IFNULL(SPC.ID_PAIS, 119) AS idPais, " +
			" IFNULL(SPC.ID_ESTADO,0) AS idEstado, " +
			" IFNULL(TB.ID_DOMICILIO, 0) AS idDomicilio, " +
			" IFNULL(REF_TELEFONO,'') as telefono," +
			" ifnull(REF_TELEFONO_FIJO,'') as telefonoFijo," +
			" IFNULL(SVD.REF_CALLE, '') AS desCalle, " +
			" IFNULL(SVD.NUM_EXTERIOR, '') AS numExterior, " +
			" IFNULL(SVD.NUM_INTERIOR, '') AS numInterior, " +
			" IFNULL(SVD.REF_CP, '') AS codigoPostal, " +
			" IFNULL(SVD.REF_COLONIA, '') AS desColonia, " +
			" IFNULL(SVD.REF_MUNICIPIO, '') AS desMunicipio, " +
			" IFNULL(SVD.REF_ESTADO, '') AS desEstado, " +
			" IFNULL(SPC.REF_CORREO, '') AS correo, " +
			" CONCAT_WS( " +
			"  ' ', SPC.NOM_PERSONA, SPC.NOM_PRIMER_APELLIDO, " +
			"  SPC.NOM_SEGUNDO_APELLIDO " +
			" ) AS nombreCompleto " +
			"FROM SVT_PLAN_SFPA SPSA " +
			" JOIN SVT_TITULAR_BENEFICIARIOS TB ON TB.ID_TITULAR_BENEFICIARIOS = SPSA.IND_TITULAR_SUBSTITUTO " +
			" AND  TB.IND_ACTIVO = 1 " +
			" JOIN SVC_PERSONA SPC ON TB.ID_PERSONA = SPC.ID_PERSONA " +
			" LEFT JOIN SVT_DOMICILIO SVD ON TB.ID_DOMICILIO = SVD.ID_DOMICILIO " +
			"WHERE SPSA.ID_PLAN_SFPA = #{idPlan}")
	public Map<String, Object> datosContratanteSustituto(@Param("idPlan") Integer datos);

	@Select(value = "SELECT " +
			" SPSA.ID_PLAN_SFPA AS idPlanSfpa, " +
			" SPC.ID_PERSONA AS idPersona, " +
			" TB.ID_TITULAR_BENEFICIARIOS AS idTitularBeneficiario, " +
			" IFNULL(TB.CVE_MATRICULA, '') AS matricula, " +
			" IFNULL(SPC.CVE_CURP, '') AS curp, " +
			" IFNULL(SPC.CVE_NSS, '') AS nss, " +
			" IFNULL(SPC.CVE_RFC, '') AS rfc, " +
			" IFNULL(SPC.NOM_PERSONA, '') AS nomPersona, " +
			" IFNULL(SPC.NOM_PRIMER_APELLIDO, '') AS primerApellido, " +
			" IFNULL(SPC.NOM_SEGUNDO_APELLIDO, '') AS segundoApellido, " +
			" IFNULL(SPC.NUM_SEXO, 0) AS idSexo, " +
			" IFNULL(SPC.REF_OTRO_SEXO, '') AS otroSexo, " +
			" CASE WHEN SPC.NUM_SEXO = 1 THEN 'MUJER' WHEN SPC.NUM_SEXO = 2 THEN 'HOMBRE' ELSE IFNULL(SPC.REF_OTRO_SEXO, '') END AS sexo, "
			+
			" SPC.FEC_NAC AS fechaNac, " +
			" IFNULL(SPC.REF_CORREO, '') AS correo, " +
			" IFNULL(REF_TELEFONO,'') as telefono," +
			" ifnull(REF_TELEFONO_FIJO,'') as telefonoFijo," +
			" CASE WHEN SPC.ID_PAIS = NULL " +
			" OR SPC.ID_PAIS = 119 THEN 1 ELSE 2 END AS idNacionalidad, " +
			" CASE WHEN SPC.ID_PAIS = NULL " +
			" OR SPC.ID_PAIS = 119 THEN 'MEXICANA' ELSE 'EXTRANJERA' END AS nacionalidad, " +
			" IFNULL(SPC.ID_PAIS, 119) AS idPais, " +
			" IFNULL(SPC.ID_ESTADO,0) AS idEstado, " +
			" IFNULL(TB.ID_DOMICILIO, 0) AS idDomicilio, " +
			" IFNULL(SVD.REF_CALLE, '') AS desCalle, " +
			" IFNULL(SVD.NUM_EXTERIOR, '') AS numExterior, " +
			" IFNULL(SVD.NUM_INTERIOR, '') AS numInterior, " +
			" IFNULL(SVD.REF_CP, '') AS codigoPostal, " +
			" IFNULL(SVD.REF_COLONIA, '') AS desColonia, " +
			" IFNULL(SVD.REF_MUNICIPIO, '') AS desMunicipio, " +
			" IFNULL(SVD.REF_ESTADO, '') AS desEstado, " +
			" CONCAT_WS( " +
			"  ' ', SPC.NOM_PERSONA, SPC.NOM_PRIMER_APELLIDO, " +
			"  SPC.NOM_SEGUNDO_APELLIDO " +
			" ) AS nombreCompleto " +
			"FROM SVT_PLAN_SFPA SPSA " +
			" JOIN SVT_TITULAR_BENEFICIARIOS TB ON TB.ID_TITULAR_BENEFICIARIOS = SPSA.ID_BENEFICIARIO_1 " +
			" AND  TB.IND_ACTIVO = 1 " +
			" JOIN SVC_PERSONA SPC ON TB.ID_PERSONA = SPC.ID_PERSONA " +
			" LEFT JOIN SVT_DOMICILIO SVD ON TB.ID_DOMICILIO = SVD.ID_DOMICILIO " +
			"WHERE SPSA.ID_PLAN_SFPA = #{idPlan}")
	public Map<String, Object> datosBeneficiario1(@Param("idPlan") Integer datos);

	@Select(value = "SELECT " +
			" SPSA.ID_PLAN_SFPA AS idPlanSfpa, " +
			" SPC.ID_PERSONA AS idPersona, " +
			" TB.ID_TITULAR_BENEFICIARIOS AS idTitularBeneficiario, " +
			" IFNULL(TB.CVE_MATRICULA, '') AS matricula, " +
			" IFNULL(SPC.CVE_CURP, '') AS curp, " +
			" IFNULL(SPC.CVE_NSS, '') AS nss, " +
			" IFNULL(SPC.CVE_RFC, '') AS rfc, " +
			" IFNULL(SPC.NOM_PERSONA, '') AS nomPersona, " +
			" IFNULL(SPC.NOM_PRIMER_APELLIDO, '') AS primerApellido, " +
			" IFNULL(SPC.NOM_SEGUNDO_APELLIDO, '') AS segundoApellido, " +
			" IFNULL(SPC.NUM_SEXO, '') AS idSexo, " +
			" IFNULL(SPC.REF_OTRO_SEXO, '') AS otroSexo, " +
			" CASE WHEN SPC.NUM_SEXO = 1 THEN 'MUJER' WHEN SPC.NUM_SEXO = 2 THEN 'HOMBRE' ELSE IFNULL(SPC.REF_OTRO_SEXO, '') END AS sexo, "
			+
			" SPC.FEC_NAC AS fechaNac, " +
			" CASE WHEN SPC.ID_PAIS = NULL " +
			" OR SPC.ID_PAIS = 119 THEN 1 ELSE 2 END AS idNacionalidad, " +
			" CASE WHEN SPC.ID_PAIS = NULL " +
			" OR SPC.ID_PAIS = 119 THEN 'MEXICANA' ELSE 'EXTRANJERA' END AS nacionalidad, " +
			" IFNULL(SPC.ID_PAIS, 119) AS idPais, " +
			" IFNULL(REF_TELEFONO,'') as telefono," +
			" ifnull(REF_TELEFONO_FIJO,'') as telefonoFijo," +
			" IFNULL(SPC.ID_ESTADO,'') AS idEstado, " +
			" IFNULL(TB.ID_DOMICILIO, '') AS idDomicilio, " +
			" IFNULL(SVD.REF_CALLE, '') AS desCalle, " +
			" IFNULL(SVD.NUM_EXTERIOR, '') AS numExterior, " +
			" IFNULL(SVD.NUM_INTERIOR, '') AS numInterior, " +
			" IFNULL(SVD.REF_CP, '') AS codigoPostal, " +
			" IFNULL(SVD.REF_COLONIA, '') AS desColonia, " +
			" IFNULL(SVD.REF_MUNICIPIO, '') AS desMunicipio, " +
			" IFNULL(SVD.REF_ESTADO, '') AS desEstado, " +
			" IFNULL(SPC.REF_CORREO, '') AS correo, " +
			" CONCAT_WS( " +
			"  ' ', SPC.NOM_PERSONA, SPC.NOM_PRIMER_APELLIDO, " +
			"  SPC.NOM_SEGUNDO_APELLIDO " +
			" ) AS nombreCompleto " +
			"FROM SVT_PLAN_SFPA SPSA " +
			" JOIN SVT_TITULAR_BENEFICIARIOS TB ON TB.ID_TITULAR_BENEFICIARIOS = SPSA.ID_BENEFICIARIO_2 " +
			" AND  TB.IND_ACTIVO = 1 " +
			" JOIN SVC_PERSONA SPC ON TB.ID_PERSONA = SPC.ID_PERSONA " +
			" LEFT JOIN SVT_DOMICILIO SVD ON TB.ID_DOMICILIO = SVD.ID_DOMICILIO " +
			"WHERE SPSA.ID_PLAN_SFPA = #{idPlan}")
	public Map<String, Object> datosBeneficiario2(@Param("idPlan") Integer datos);

	@Select(value = "SELECT " +
			" SPSA.ID_PAQUETE AS idPaquete, " +
			" SPSA.IND_TITULAR_SUBSTITUTO AS indTitularSubstituto, " +
			" CASE WHEN COUNT(SPS.ID_PAGO_SFPA) = 0 " +
			" THEN 0 " +
			" ELSE 1 END AS pago, " +
			" SPSA.IND_PROMOTOR	AS indPromotor," +
			" IFNULL(SPSA.ID_PROMOTOR,0) AS idPromotor, " +
			" SP.REF_PAQUETE_NOMBRE AS nombrePaquete, " +
			" SPSA.IMP_PRECIO AS costoPaquete, " +
			" IFNULL(CONCAT(SP2.NOM_PROMOTOR,' ',SP2.NOM_PAPELLIDO,'',SP2.NOM_SAPELLIDO),'') AS nombrePromotor " +
			" FROM " +
			" SVT_PLAN_SFPA SPSA " +
			" INNER JOIN SVT_PAGO_SFPA SPS  ON " +
			" SPSA.ID_PLAN_SFPA  = SPS.ID_PLAN_SFPA " +
			" INNER JOIN SVT_PAQUETE SP ON SPSA.ID_PAQUETE = SP.ID_PAQUETE " +
			" LEFT JOIN SVT_PROMOTOR SP2 ON SPSA.ID_PROMOTOR = SP2.ID_PROMOTOR " +
			" WHERE " +
			" SPSA.ID_PLAN_SFPA = #{idPlan} AND  SPS.ID_ESTATUS_PAGO = 5 ")
	public Map<String, Object> datosPlan(@Param("idPlan") Integer datos);

	@Update(value = "UPDATE" +
			" SVT_PAGO_SFPA" +
			" SET" +
			" IND_ACTIVO = 0," +
			" FEC_ACTUALIZACION = CURRENT_TIMESTAMP()," +
			" ID_USUARIO_MODIFICA = #{in.idUsuario} " +
			"WHERE ID_PLAN_SFPA = #{in.idPlanSfpa} ")
	public int cancelaPagos(@Param("in") PlanSFPA persona);

	@Update(value = "UPDATE" +
			" SVT_TITULAR_BENEFICIARIOS  " +
			" SET" +
			" CVE_MATRICULA   = #{in.matricula}, " +
			" ID_PERSONA   = #{in.idPersona}," +
			" ID_DOMICILIO   = #{in.idDomicilio}," +
			" IND_ACTIVO   = 1," +
			" FEC_ACTUALIZACION   = CURRENT_TIMESTAMP(), " +
			" ID_USUARIO_MODIFICA   = #{in.idUsuario} " +
			" WHERE" +
			" ID_TITULAR_BENEFICIARIOS   = #{in.idTitularBeneficiario}")
	public int actualizaTitulaBeneficiarioPersonaDomicilio(@Param("in") PlanSFPA persona);

	@Update(value = "UPDATE" +
			" SVT_TITULAR_BENEFICIARIOS  " +
			" SET" +
			" IND_ACTIVO   = 0," +
			" FEC_ACTUALIZACION   = CURRENT_TIMESTAMP(), " +
			" ID_USUARIO_MODIFICA   = #{in.idUsuario} " +
			" WHERE" +
			" ID_TITULAR_BENEFICIARIOS   = #{in.idTitularBeneficiario}")
	public int bajaTitulaBeneficiario(@Param("in") PlanSFPA persona);

	@Update(value = ""
			+ "UPDATE SVT_DOMICILIO  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_TIMESTAMP(), "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ," +
			" REF_CALLE = #{in.desCalle} , " +
			" NUM_EXTERIOR= #{in.numExterior} , " +
			" NUM_INTERIOR = #{in.numInterior} , " +
			" REF_COLONIA = #{in.desColonia} , " +
			" REF_MUNICIPIO = #{in.desMunicipio} ,  " +
			" REF_ESTADO = #{in.desEstado}   "
			+ " WHERE ID_DOMICILIO = #{in.idDomicilio} ")
	public int actulizaDomicilioContratante(@Param("in") PlanSFPA persona);

	@Update(value = ""
			+ "UPDATE SVC_PERSONA  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_TIMESTAMP(), "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ," +
			" REF_TELEFONO = #{in.telefono} , " +
			" REF_TELEFONO_FIJO = #{in.telefonoFijo} ,  " +
			" REF_CORREO = #{in.correo}   " +
			" WHERE ID_PERSONA = #{in.idPersona} ")
	public int actulizaPersonaContratante(@Param("in") PlanSFPA persona);

	@Update(value = ""
			+ "UPDATE SVC_CONTRATANTE  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_TIMESTAMP(), "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ," +
			" CVE_MATRICULA = #{in.matricula}  " +
			" WHERE ID_CONTRATANTE = #{in.idContratante} ")
	public int actulizaMatriculaContratante(@Param("in") PlanSFPA persona);

	@Update(value = ""
			+ "UPDATE SVT_PLAN_SFPA  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_TIMESTAMP(), "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ," +
			" ID_TITULAR_SUBSTITUTO  = #{in.idTitularBeneficiario} , " +
			" IND_TITULAR_SUBSTITUTO = #{in.indTitularSubstituto}   " +
			" WHERE ID_PLAN_SFPA = #{in.idPlanSfpa} ")
	public int actulizaTitularSustitutoPlan(@Param("in") PlanSFPA persona);

	@Update(value = ""
			+ "UPDATE SVT_PLAN_SFPA  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_TIMESTAMP(), "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ," +
			" ID_BENEFICIARIO_1  = #{in.idTitularBeneficiario} " +
			" WHERE ID_PLAN_SFPA = #{in.idPlanSfpa} ")
	public int actulizaBeneficiario1Plan(@Param("in") PlanSFPA persona);

	@Update(value = ""
			+ "UPDATE SVT_PLAN_SFPA  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_TIMESTAMP(), "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ," +
			" ID_BENEFICIARIO_2  = #{in.idTitularBeneficiario}  " +
			" WHERE ID_PLAN_SFPA = #{in.idPlanSfpa} ")
	public int actulizaBeneficiario2Plan(@Param("in") PlanSFPA persona);

	@Select(value = "SELECT ID_PERSONA AS idPersona FROM  SVC_PERSONA " +
			" WHERE CVE_CURP = #{curp} LIMIT 1 ")
	public Map<String, Object> buscaCurp(@Param("curp") String curp);

	@Select(value = "SELECT ID_PERSONA AS idPersona FROM  SVC_PERSONA " +
			" WHERE CVE_RFC = #{rfc} LIMIT 1 ")
	public Map<String, Object> buscaRFC(@Param("rfc") String rfc);

	@Update(value = ""
			+ "UPDATE SVT_TITULAR_BENEFICIARIOS  "
			+ "SET  "
			+ "FEC_ACTUALIZACION = CURRENT_TIMESTAMP(), "
			+ "ID_USUARIO_MODIFICA = #{in.idUsuario} ," +
			" CVE_MATRICULA = #{in.matricula} " +
			" WHERE ID_TITULAR_BENEFICIARIOS = #{in.idTitularBeneficiario} ")
	public int actulizaMatricula(@Param("in") PlanSFPA persona);

}
