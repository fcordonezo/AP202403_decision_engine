package co.com.pragma.decision_engine;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info=@Info(title="Motor de decisiones"))
@SpringBootApplication
public class DecisionEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecisionEngineApplication.class, args);
	}

}
