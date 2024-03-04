package co.com.pragma.decision_engine.application.handler;

import co.com.pragma.decision_engine.application.dto.DecisionEngineRequestDto;
import co.com.pragma.decision_engine.application.dto.DecisionEngineResponseDto;

public interface DecisionEngineHandler {
  DecisionEngineResponseDto run(DecisionEngineRequestDto dto);
}
