/**
 * 
 */
package com.isn.network.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//import io.swagger.models.Contact;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author tcts-india
 *
 */
@Configuration
@EnableSwagger2
public class IsnNetworkConfig  extends WebMvcConfigurationSupport {
	
	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)          
	      .select()
	      .apis(RequestHandlerSelectors.basePackage("com.isn.network.controllers"))
	      .paths(PathSelectors.ant("/*"))
	      .build()
	      .apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "International Students Network API", 
	      "Description of API Resources.", 
	      "API TOS", 
	      "Terms of service", 
	      new Contact("Venkat Singi Reddy", "https://bixrlabs.herokuapp.com", "venkat@bixrlabs.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		 
		registry.addResourceHandler("swagger-ui.html")
         .addResourceLocations("classpath:/META-INF/resources/");

		 registry.addResourceHandler("/webjars/**")
		         .addResourceLocations("classpath:/META-INF/resources/webjars/");
		 
		 
	}	
	
	

}
