package co.com.pragma.decision_engine;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info=@Info(title="Motor de decisiones", version = "1.0.0"))
@SecurityScheme(name = "BearerAuthentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@SpringBootApplication
public class DecisionEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecisionEngineApplication.class, args);
	}

}
