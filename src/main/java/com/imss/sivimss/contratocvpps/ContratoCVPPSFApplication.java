package com.imss.sivimss.contratocvpps;

import java.time.Duration;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.imss.sivimss.contratocvpps.util.NoRedirectSimpleClientHttpRequestFactory;

@SpringBootApplication
public class ContratoCVPPSFApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContratoCVPPSFApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().requestFactory(NoRedirectSimpleClientHttpRequestFactory.class)
				.setConnectTimeout(Duration.ofMillis(195000)).setReadTimeout(Duration.ofMillis(195000)).build();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
