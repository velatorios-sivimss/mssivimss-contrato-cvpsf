package com.imss.sivimss.contratocvpps.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Persona {

	private Integer idPersona;
	private String persona;
	private String rfc;
	private String curp;
	private String nss;
	private String nomPersona;
	private String primerApellido;
	private String segundoApellido;
	private String sexo;
	private String otroSexo;
	private String fecNacimiento;
	private String nacionalidad;
	private Integer idPais;
	private Integer idEstado;
	private String telefono;
	private String telefonoFijo;
	private String correo;
	private String tipoPersona;
	private String ine;
	
}
