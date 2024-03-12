package co.com.pragma.decision_engine.domain.api;

import co.com.pragma.decision_engine.domain.model.DecisionEngine;

public interface RunServicePort {
  DecisionEngine run(DecisionEngine decisionEngine);
}
