package com.gg.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置类
 * @author: GG
 * @date: 2021/2/25 9:46 下午
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createDocketApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gg.server.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("医护端接口文档")
                .contact(new Contact("cgg","http:localhost:8081/doc.html","ggguang12138@icloud.com")).build();
    }

    private List<ApiKey> securitySchemes(){
        // 设置全局请求头信息
        List<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization","Authorization","header");
        result.add(apiKey);
        return result;
    }
    
    private List<SecurityContext> securityContexts(){
        // 设置需要进行认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/doctor/*"));
        return result;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        // 默认授权的角色
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "全局");
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization",authorizationScopes));
        return result;

    }

}
