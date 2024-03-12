package co.com.pragma.decision_engine.domain.model;

import lombok.Builder;

@Builder
public record FinanceProduct(
  Long financeProductId,
  String code,
  String description,
  String ruleSet
) {
}