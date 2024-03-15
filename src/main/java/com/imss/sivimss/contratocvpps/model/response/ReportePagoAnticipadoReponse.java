package com.imss.sivimss.contratocvpps.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportePagoAnticipadoReponse {

private String nombreTitular;
private String numFolioPlanSFPA;
private String fecNacTitular;
private String nacionalidadTitular;
private String rfcTitular;
private String calleTitular;
private String numExterior;
private String numInterior;
private String colonia;
private String codigoPostal;
private String municipio;
private String estado;
private String correo;
private String telefono;
private String telefonoFijo;
private String totalImporte;
private String numPago;
private String ciudadFirma;
private String fechaFirma;
private String nomPaquete;
private String pagos;
private String servInclPaquete;
private String correoVelatorio;
private String numeroAfiliacion;
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
private String canPagoPalabras;
private String firmDir;
private String imgCheck;
private String nomFibeso;
private Boolean indTitularSubs;

}
