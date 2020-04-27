package com.lexisnexis.assessment.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Sudhakaran Vasudevan
 * 
 * Swagger configuration to document api endpoints
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2UiConfiguration{
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.lexisnexis.assessment.controller"))
            .build();
    }	
	
}
