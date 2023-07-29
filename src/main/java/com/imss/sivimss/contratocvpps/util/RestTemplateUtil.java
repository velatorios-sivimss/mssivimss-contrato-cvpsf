package com.imss.sivimss.contratocvpps.util;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.imss.sivimss.contratocvpps.model.request.PersonaCurpRequest;
import com.imss.sivimss.contratocvpps.model.request.PersonaRfcRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestTemplateUtil {

	private final RestTemplate restTemplate;

	public RestTemplateUtil(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Env&iacute;a una petici&oacute;n con Body.
	 *
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Response<Object> sendPostRequestByteArray(String url, EnviarDatosRequest body, Class<?> clazz)
			throws IOException {
		Response<Object> responseBody = new Response();
		HttpHeaders headers = RestTemplateUtil.createHttpHeaders();

		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		ResponseEntity<Object> responseEntity = null;
		try {
			responseEntity = (ResponseEntity<Object>) restTemplate.postForEntity(url, request, clazz);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
				responseBody = (Response<Object>) responseEntity.getBody();
			} else {
				throw new IOException("Ha ocurrido un error al enviar");
			}
		} catch (IOException ioException) {
			throw ioException;
		} catch (Exception e) {
			log.error("Fallo al consumir el servicio, {}", e.getMessage());
			responseBody.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseBody.setError(true);
			responseBody.setMensaje(e.getMessage());
		}

		return responseBody;
	}

	/**
	 * Env&iacute;a una petici&oacute;n con Body y token.
	 *
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Response<Object> sendPostRequestByteArrayToken(String url, EnviarDatosRequest body, String subject,
			Class<?> clazz) {
		Response<Object> responseBody = new Response();
		HttpHeaders headers = RestTemplateUtil.createHttpHeadersToken(subject);

		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		ResponseEntity<Object> responseEntity = null;

		responseEntity = (ResponseEntity<Object>) restTemplate.postForEntity(url, request, clazz);

		responseBody = (Response<Object>) responseEntity.getBody();

		return responseBody;
	}
	
	/**
	 * Env&iacute;a una petici&oacute;n con Body y token.
	 *
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Response<Object> sendPostRequestByteArrayTokenObject(String url, EnviarDatosRequest body, String subject,
			Class<?> clazz) {
		HttpHeaders headers = RestTemplateUtil.createHttpHeadersToken(subject);

		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		ResponseEntity<Object> responseEntity = null;

		responseEntity = (ResponseEntity<Object>) restTemplate.postForEntity(url, request, clazz);

		return (Response<Object>) responseEntity.getBody();
	}
	
	/**
	 * Env&iacute;a una petici&oacute;n con Body y token.
	 *
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Response<Object> sendPostRequestByteArrayToken(String url, Object body, String subject,
			Class<?> clazz) {
		HttpHeaders headers = RestTemplateUtil.createHttpHeadersToken(subject);

		HttpEntity<Object> request = new HttpEntity<>(body, headers);

		 ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) restTemplate.postForEntity(url, request, clazz);

		return (Response<Object>) responseEntity.getBody();
	}
	
    /**
     * Env&iacute;a una petici&oacute;n de tipo POST a la url que se seleccione
     *
     * @param url
     * @param clazz
     * @return
     */
    public Response<Object> sendGetRequestRfc(String url)  {
    	Response<Object> response = new Response<>();
    	ResponseEntity<PersonaRfcRequest> responseEntity = null;
        try {
        	responseEntity = restTemplate.getForEntity(url, PersonaRfcRequest.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK ) {
            	response = Response.builder().codigo(200).error(false)
				.mensaje("exito").datos(responseEntity.getBody()).build();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            response.setError(true);
            response.setCodigo(404);
            response.setMensaje(e.getMessage());
        }
        return response;
    }
    
    /**
     * Env&iacute;a una petici&oacute;n de tipo POST a la url que se seleccione
     *
     * @param url
     * @param clazz
     * @return
     */
    public Response<Object> sendGetRequestCurp(String url)  {
    	Response<Object> response = new Response<>();
    	ResponseEntity<PersonaCurpRequest> responseEntity = null;
        try {
        	responseEntity = restTemplate.getForEntity(url, PersonaCurpRequest.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK ) {
            	response = Response.builder().codigo(200).error(false)
				.mensaje("exito").datos(responseEntity.getBody()).build();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            response.setError(true);
            response.setCodigo(404);
            response.setMensaje(e.getMessage());
        }
        return response;
    }

	/**
	 * Crea los headers para la petici&oacute;n falta agregar el tema de seguridad
	 * para las peticiones
	 *
	 * @return
	 */
	private static HttpHeaders createHttpHeaders() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return header;
	}

	/**
	 * Crea los headers para la petici&oacute;n con token  - falta agregar el
	 * tema de seguridad para las peticiones
	 *
	 * @return
	 */
	private static HttpHeaders createHttpHeadersToken(String subject) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("Authorization", "Bearer " + subject);

		header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return header;
	}
	
//////////////////////////////////////////
	/**
	 * Enviar una peticion con Body para reportes.
	 *
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Response<Object> sendPostRequestByteArrayReportesToken(String url, DatosReporteDTO body, String subject,
			Class<?> clazz) {
		Response<Object> responseBody = new Response();
		HttpHeaders headers = RestTemplateUtil.createHttpHeadersToken(subject);

		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		ResponseEntity<Object> responseEntity = null;
		responseEntity = (ResponseEntity<Object>) restTemplate.postForEntity(url, request, clazz);
		responseBody = (Response<Object>) responseEntity.getBody();

		return responseBody;
	}
}
