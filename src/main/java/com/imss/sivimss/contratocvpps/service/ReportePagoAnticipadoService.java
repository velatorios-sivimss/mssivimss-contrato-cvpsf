package com.imss.sivimss.contratocvpps.service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.security.core.Authentication;

import com.imss.sivimss.contratocvpps.util.DatosRequest;
import com.imss.sivimss.contratocvpps.util.Response;

public interface ReportePagoAnticipadoService {

    Response<Object> generaReporteConvenioPagoAnticipado(DatosRequest request, Authentication authentication) throws IOException;
}
