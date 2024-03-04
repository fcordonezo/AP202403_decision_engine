package co.com.pragma.decision_engine.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record DecisionEngine(
  Customer customer,
  List<FinanceService> financeServiceList
) {
}
