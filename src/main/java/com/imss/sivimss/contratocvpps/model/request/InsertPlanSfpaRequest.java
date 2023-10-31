package com.imss.sivimss.contratocvpps.model.request;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InsertPlanSfpaRequest {

	 @JsonProperty
	 private ArrayList<String> insertar;
	 
	 @JsonProperty
	 private ArrayList<String> actualizar;
	 
	 @JsonProperty
	 private ArrayList<String> insertar2;

}
