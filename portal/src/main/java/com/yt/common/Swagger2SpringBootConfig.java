package com.yt.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@Configuration
@EnableWebMvc
@Component
public class Swagger2SpringBootConfig {

	private Boolean docketEnable= Boolean.valueOf(true);

	@Bean
	public Docket defaultAll() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Default-ALL")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.yt.portal.controller")).paths(PathSelectors.any())
				.build()
				.forCodeGeneration(true)
				.apiInfo(baseApiInfo("Default All Rest Full Api")).enable(docketEnable);
	}
	private ApiInfo baseApiInfo(String modelName) {
		return new ApiInfo("运图WEB API接口", modelName, "1.4.0", null, new Contact("", null, ""), null, null);
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration(
				null,
//				"/v2/api-docs",// validate url
				"pageBrand",       // docExpansion          => none | pageBrand
				"alpha",      // apiSorter             => alpha
				"schema",     // defaultModelRendering => schema
				false,        // enableJsonEditor      => true | false
				true);        // showRequestHeaders    => true | false
	}
}
