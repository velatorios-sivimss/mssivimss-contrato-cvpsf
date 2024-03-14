package com.imss.sivimss.contratocvpps.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagosSFPA {
    private Integer idPlanSfpa;
    private Integer idPago;
    private double montoParcialidad;
    private String fechaParcialidad;
    private Integer idUsuario;
    private Integer noMes;
    private Integer idPagoSFPA;
    private Integer idEstatusPago;
    

}
