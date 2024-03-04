package co.com.pragma.decision_engine.application.handler;

import co.com.pragma.decision_engine.application.dto.DecisionEngineRequestDto;
import co.com.pragma.decision_engine.application.dto.DecisionEngineResponseDto;
import co.com.pragma.decision_engine.application.mapper.DecisionEngineApiMapper;
import co.com.pragma.decision_engine.domain.api.RunServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DecisionEngineHandlerImpl implements DecisionEngineHandler {

  private final RunServicePort readDecisionEngineServicePort;
  private final DecisionEngineApiMapper decisionEngineApiMapper;

  @Autowired
  public DecisionEngineHandlerImpl(
    RunServicePort readDecisionEngineServicePort,
    DecisionEngineApiMapper decisionEngineApiMapper
  ) {
    this.readDecisionEngineServicePort = readDecisionEngineServicePort;
    this.decisionEngineApiMapper = decisionEngineApiMapper;
  }

  @Override
  public DecisionEngineResponseDto run(DecisionEngineRequestDto dto) {
    return decisionEngineApiMapper.toDecisionEngineResponseDto(
      readDecisionEngineServicePort.run(decisionEngineApiMapper.toDecisionEngine(dto))
    );
  }
}
