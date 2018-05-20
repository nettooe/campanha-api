package com.nettooe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Configuration
public class CampanhaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampanhaApiApplication.class, args);
	}

//	@Bean
//	ServletRegistrationBean h2servletRegistration() {
//		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
//		registrationBean.addUrlMappings("/console/*");
//		return registrationBean;
//	}
}
