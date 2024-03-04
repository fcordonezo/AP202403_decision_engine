package co.com.pragma.decision_engine.domain.model;

import lombok.Builder;

@Builder
public record FinanceService(
  Long financeServiceId,
  String code,
  String description,
  String ruleSet
) {
}