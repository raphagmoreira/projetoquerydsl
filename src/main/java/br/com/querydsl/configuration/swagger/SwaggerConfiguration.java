package br.com.querydsl.configuration.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Autowired
    private DocketFactory docketFactory;

    @Bean
    public Docket api() {
        return docketFactory.createApi("Autenticacao", "/authentication/**", Boolean.FALSE);
    }

    @Bean
    public Docket authentication() {
        return docketFactory.createApi("Api", "/api/**", Boolean.TRUE);
    }

}
