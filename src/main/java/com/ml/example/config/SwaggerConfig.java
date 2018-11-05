package com.ml.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	// �����������������Ĺ���
	@Value("${swagger.ui.enable}")
	private boolean environmentSpecificBooleanFlag;

	@Bean
	public Docket docketFactory() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(
						new ApiInfo("�ӿ��ĵ�", "SpingCloud web�ӿ��б�", "1.0", "",
								"", "", "")).enable(
						Boolean.valueOf(environmentSpecificBooleanFlag));
	}
}
