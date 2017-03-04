package id.co.blogspot.fathan.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/** Created by 4than.mustaqiim on 3/4/2017. */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Bean
  public Docket docket() {
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(
        new ParameterBuilder()
            .name("Authorization")
            .description("authorization")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .build());
    return new Docket(DocumentationType.SWAGGER_2)
        .globalOperationParameters(parameters)
        .select()
        .apis(RequestHandlerSelectors.basePackage("id.co.blogspot.fathan.controller"))
        .paths(PathSelectors.ant("/api/**"))
        .build();
  }
}
