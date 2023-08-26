package com.imss.sivimss.contratocvpps.util;

import com.google.gson.Gson;
import com.imss.sivimss.contratocvpps.model.request.UsuarioDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class LogUtil {
	
    @Value("${ruta-log}")
    private String rutaLog;
    
    @Value("${spring.application.name}")
	private String mssivimssContratoCvpps;
    
    private static String RETORNO = "\r\n";

    private String formatoFechaLog = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogUtil.class);


    public void crearArchivoLog(String tipoLog, String origen, String clasePath, String mensaje, String tiempoEjecucion, Authentication authentication) throws IOException {
        Gson json = new Gson();
        UsuarioDto usuarioDto = json.fromJson((String) authentication.getPrincipal(), UsuarioDto.class);
        File archivo = new File(rutaLog + mssivimssContratoCvpps + new SimpleDateFormat("ddMMyyyy").format(new Date()) + ".log");
        FileWriter escribirArchivo=null;
        try { 
            escribirArchivo = new FileWriter(archivo, true);
            escribirArchivo.write("" + formatoFechaLog + " --- [" + tipoLog + "] " + origen + " " + clasePath + " : " + mensaje + " , Usuario: " + usuarioDto.getCveUsuario() + " - " + tiempoEjecucion);
            escribirArchivo.write("\r\n");
            escribirArchivo.close();
            escribirArchivo.close();
        } catch (Exception e) {
            log.error("No se puede escribir el log.");
            log.error(e.getMessage());
        } finally {
            if (escribirArchivo!=null) {
                escribirArchivo.close();
            }  
        }
    }
    
 public void crearArchivoLog(String tipoLog, String origen, String clasePath, String mensaje, String tiempoEjecucion) throws IOException {
        try(FileWriter escribirArchivo = new FileWriter(new File(rutaLog + mssivimssContratoCvpps + new SimpleDateFormat("ddMMyyyy").format(new Date()) + ".log"), true) ) {
        	String peticion = "" + formatoFechaLog + " --- [" + tipoLog + "] " + origen + " " + clasePath + " : " + mensaje + " - " + tiempoEjecucion;
        	log.info(peticion);
            escribirArchivo.write(peticion);
            escribirArchivo.write(RETORNO);
            escribirArchivo.close();
        } catch (Exception e) {
            log.error("No se puede escribir el log.");
            log.error(e.getMessage());
        }

    }

}
