package com.imss.sivimss.contratocvpps.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportePagoAnticipadoReponse {

private String ciudadFirma;
private String totalImporte;
private String canPagoPalabras;
private String nomPaquete;
private String nombreTitular;
private String rfcTitular;
private String nacionalidadTitular;
private String numPago;
private String correoVelatorio;
private String fechaFirma;
private String cuotaAfiliacion;
private String numeroAfiliacion;
private String servInclPaquete;
private String nombreSustituto;
private String fecNacSustituto;
private String rfcSustituto;
private String telefonoSustituto;
private String direccionSustituto;
private String nombreB1;
private String fecNacB1;
private String rfcB1;
private String telefonoB1;
private String direccionB1;
private String nombreB2;
private String fecNacB2;
private String rfcB2;
private String telefonoB2;
private String direccionB2;
private String firmDir;
private String imgCheck;
}
