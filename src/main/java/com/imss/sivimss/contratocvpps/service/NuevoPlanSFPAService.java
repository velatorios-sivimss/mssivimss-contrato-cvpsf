package com.imss.sivimss.contratocvpps.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import com.imss.sivimss.contratocvpps.util.Response;

public interface NuevoPlanSFPAService {

	public Response<Object> detallePlanSFPA(Integer idPlanSFPA, Authentication authentication)
			throws IOException;

}
