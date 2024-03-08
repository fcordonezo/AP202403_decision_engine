package co.com.pragma.decision_engine.infrastructure.in.rest;

import co.com.pragma.decision_engine.application.dto.DecisionEngineRequestDto;
import co.com.pragma.decision_engine.application.dto.DecisionEngineResponseDto;
import co.com.pragma.decision_engine.application.handler.DecisionEngineHandler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DecisionEngineController {
  private final DecisionEngineHandler decisionEngineHandler;

  @Autowired
  public DecisionEngineController(DecisionEngineHandler decisionEngineHandler) {
    this.decisionEngineHandler = decisionEngineHandler;
  }

  @Operation(summary = "Ejecutar motor de decisiones")
  @PostMapping(path = "/run", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DecisionEngineResponseDto> run(@RequestBody DecisionEngineRequestDto dto) {
    DecisionEngineResponseDto decisionEngineResponseDto = decisionEngineHandler.run(dto);
    return ResponseEntity.ok(decisionEngineResponseDto);
  }
}
