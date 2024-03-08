package co.com.pragma.decision_engine.application.dto;

import lombok.Builder;

@Builder
public record FinanceProductDto(
  Long financeProductId,
  String code,
  String description,
  String ruleSet
) {
}
