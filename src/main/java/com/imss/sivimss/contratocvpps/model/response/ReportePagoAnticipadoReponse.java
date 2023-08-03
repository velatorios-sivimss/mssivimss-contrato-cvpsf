package com.imss.sivimss.contratocvpps.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportePagoAnticipadoReponse {

private String lugarFirma;
private String canPagoNum;
private String canPagoPalabras;
private String paqueteAmparo;
private String nombreAfiliado;
private String numPagos;
private String rfc;
private String fechaFirma;
private String cuotaAfiliacion;
private String numeroAfiliacion;
private String servInclPaquete;
private String firmDir;
private String imgCheck;
}
