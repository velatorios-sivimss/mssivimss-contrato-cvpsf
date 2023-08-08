package com.imss.sivimss.contratocvpps.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.Response;

public interface ConsultaPlanSFPAService {
	
	Response<Object> consultaPlanSFPA(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> generarReportePlanSFPA(DatosRequest request, Authentication authentication) throws IOException;

}
