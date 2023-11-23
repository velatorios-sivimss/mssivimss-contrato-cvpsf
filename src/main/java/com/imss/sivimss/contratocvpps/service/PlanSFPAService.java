package com.imss.sivimss.contratocvpps.service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.Response;

public interface PlanSFPAService {
	
    Response<Object> detalleContratanteRfc(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> detalleContratanteCurp(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> consultaTipoContratacion(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> consultaTipoPagoMensual(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> consultaPromotores(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> consultaPaquetes(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> consultaValidaAfiliado(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object>  registrarPlanSFPA(DatosRequest request, Authentication authentication) throws IOException, SQLException;
	
	Response<Object>  actualizarPlanSFPA(DatosRequest request, Authentication authentication) throws IOException, SQLException;
	
	Response<Object>  cancelaPlanSFPA(DatosRequest request, Authentication authentication) throws IOException, SQLException;
	
	Response<Object>  consultaDetallePlanSfpa(DatosRequest request, Authentication authentication) throws IOException, SQLException;
	
	Response<Object> consultarFolioOrden(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object>  consultaDetalleLineaPlanSFPA(DatosRequest request, Authentication authentication) throws IOException, SQLException;

}
