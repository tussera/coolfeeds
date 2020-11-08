package com.ggcorreia.coolfeeds;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoolfeedsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoolfeedsApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String appDescription,
                                 @Value("${application-version}") String appVersion){
        Info apiInfo = new Info()
                .title("Cool Feeds API")
                .version(appVersion)
                .description(appDescription)
                .termsOfService("http://swagger.io/terms/")
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org"));

        return new OpenAPI().info(apiInfo);
    }
}
