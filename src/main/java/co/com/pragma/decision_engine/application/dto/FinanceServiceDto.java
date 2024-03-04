package co.com.pragma.decision_engine.application.dto;

import lombok.Builder;

@Builder
public record FinanceServiceDto(
  Long financeServiceId,
  String code,
  String description,
  String ruleSet
) {
}
