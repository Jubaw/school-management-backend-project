package com.project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
//Info parametresi,API'nin başlığını ve sürümünü içeren bir @Info nesnesi alır.
// Security parametresi, API'ye erişmek için gereken güvenlik gereksinimlerini duyurur.

@OpenAPIDefinition(info = @Info(title = "StudentManagement API", version = "1.0.0"),
                                                    security = @SecurityRequirement(name = "Bearer"))

// Name parametresi, güvenlik şemasının adını belirtir.Type parametresi,
// güvenlik türünü belirtir ve SecuritySchemeType.HTTP değeri kullanılarak HTTP güvenlik şeması belirlenir.
@SecurityScheme(name = "Bearer",type = SecuritySchemeType.HTTP,scheme = "Bearer")
public class OpenApiConfig {

    // http://localhost:8080/swagger-ui/index.html --- > Swagger API erişim adresi


}
