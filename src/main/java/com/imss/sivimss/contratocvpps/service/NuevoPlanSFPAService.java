package com.imss.sivimss.contratocvpps.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.Response;

public interface NuevoPlanSFPAService {

	public Response<Object> detallePlanSFPA(Integer idPlanSFPA, Authentication authentication)
			throws IOException;

	public Response<Object> busquedaPlanSFPA(DatosRequest paginado, Authentication authentication)
			throws IOException;
	
	public Response<Object> insertarPlanSFPA(DatosRequest planSFPA, Authentication authentication)
			throws IOException;
	
	public Response<Object> busquedaDetallePlanSFPA(DatosRequest paginado, Authentication authentication)
			throws IOException;
	
	public Response<Object> actualizarPlanSFPA(DatosRequest planSFPA, Authentication authentication)
			throws IOException;


}
