package com.imss.sivimss.contratocvpps.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanSFPA {
    private Integer idPlanSfpa;
    private String numFolio;
    private Integer idTipoContratacion;
    private Integer idPaquete;
    private double monPrecio;
    private Integer idTipoPagoMensual;
    private Integer indTitularSubstituto;
    private Integer indModificarTitularSubstituto;
    private Integer indPromotor;
    private Integer idPromotor;
    private String fechaIngreso;
    private String horaIngreso;
    private Integer idVelatorio;
    private Integer idEstatusPlan;
    private String persona;
    private Integer idTitular;
    private Integer idContratante;
    private Integer idPersona;
    private String rfc;
    private String curp;
    private String matricula;
    private String nss;
    private String nomPersona;
    private String primerApellido;
    private String segundoApellido;
    private String sexo;
    private String otroSexo;
    private String fecNacimiento;
    private Integer idPais;
    private Integer idEstado;
    private String telefono;
    private String telefonoFijo;
    private String correo;
    private String tipoPersona;
    private String ine;
    private Integer idDomicilio;
    private String desCalle;
    private String numExterior;
    private String numInterior;
    private String codigoPostal;
    private String desColonia;
    private String desMunicipio;
    private String desEstado;
    private Integer idTitularSubstituto;
    private Integer idBeneficiario1;
    private Integer idBeneficiario2;
    private String paquete;
    private String nombreCompleto;
    private String estatusPlan;
    private String estatusPago;
    private String estado;
    private String noPago;
    private String fechaInicio;
    private String fechaFin;
    private Integer idUsuario;
    private Integer idSexo;
    private Integer idTitularBeneficiario;
    private Integer pagoMensual;
    private Integer idNacionalidad;
    private String nacionalidad;
    private Integer pago;
    private Integer cambioParcialidad;
    private String contrasenia;
    private String usuario;

}
