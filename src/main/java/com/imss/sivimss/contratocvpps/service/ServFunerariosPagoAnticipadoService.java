package com.imss.sivimss.contratocvpps.service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.Response;

public interface ServFunerariosPagoAnticipadoService {
	
    Response<Object> detalleContratanteRfc(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> detalleContratanteCurp(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> consultaTipoContratacion(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> consultaTipoPagoMensual(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> consultaPromotores(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> consultaPaquetes(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object> consultaValidaAfiliado(DatosRequest request, Authentication authentication) throws IOException;
	
	Response<Object>  insertaPlanSFPA(DatosRequest request, Authentication authentication) throws IOException, SQLException;
	
	Response<Object>  cancelaPlanSFPA(DatosRequest request, Authentication authentication) throws IOException, SQLException;

}
