package com.nettooe.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.nettooe.resource")).paths(PathSelectors.any()).build()
				.apiInfo(getApInfo());
	}

	private ApiInfo getApInfo() {
		return new ApiInfo("Campanha REST API", "Esta é uma API para gerenciamento de Campanhas.", "1.0",
				"termos do serviço", new Contact("Óliver Emanuel", "www.todaciencia.com.br", "netto.oe@gmail.com"),
				"Licença Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
	}

}
