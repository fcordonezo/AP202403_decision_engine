package co.com.pragma.decision_engine.application.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;

import java.util.List;

@Builder
public record DecisionEngineRequestDto(
  @JsonAlias("customer")
  CustomerDto customerDto,

  @JsonAlias("financeServices")
  List<FinanceServiceDto> financeServiceDtoList
) {
}
