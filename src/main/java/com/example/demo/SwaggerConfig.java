package com.example.demo;

import static java.util.Arrays.asList;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@EnableSwagger2WebFlux
@Profile("!sandpit")
public class SwaggerConfig {
  @Bean
  public Docket api(ServerProperties serverProperties) {

    Parameter parameter = new ParameterBuilder().name("My-Client-Type").description("My Client Type Header").modelRef(new ModelRef("string")).parameterType("header").required(true)
        .allowableValues(new AllowableListValues(asList("browser", "android", "ios"), "BROWSER")).defaultValue("swagger").build();

    return new Docket(DocumentationType.SWAGGER_2).pathMapping(serverProperties.getServlet().getContextPath()).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.example.demo"))
        .paths(PathSelectors.ant("/**")).build().globalOperationParameters(asList(parameter));

  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("My API").description("My API").contact(new Contact("Dave", null, "dave@dave.com")).license("Dave LTD").version("1.0").build();
  }
}
