package br.com.querydsl.configuration.swagger;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.models.auth.In;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static springfox.documentation.builders.PathSelectors.regex;

@Component
public class DocketFactory {

    public Docket createApi(String groupName, String pathRecurso, Boolean authentication) {
        Set<String> retornoList = new HashSet();
        retornoList.add(MediaType.APPLICATION_JSON_VALUE);

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.querydsl.application.resource"))
                .paths(PathSelectors.ant(pathRecurso))
                .build()
                .consumes(retornoList)
                .produces(retornoList)
                .apiInfo(apiInfo());

        if (authentication) {
            docket.securitySchemes(Collections.singletonList(new ApiKey("http", HttpHeaders.AUTHORIZATION, In.HEADER.name())));
            docket.securityContexts(Collections.singletonList(securityContext()));
        }

        return docket;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(
                        new SecurityReference("http",
                                new AuthorizationScope[0])))
                .build();
    }

    //Informacoes complementares da documentacao para o usuario final
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Estudo - Spring API",
                "Projeto destinado para estudos.",
                "1.0.0",
                null,
                new Contact(
                        "Raphael Garcia Moreira",
                        "https://github.com/raphagmoreira/projetoquerydsl",
                        "raphagmoreira@gmail.com"
                ),
                null,
                null,
                Collections.emptyList()
        );
    }

}
