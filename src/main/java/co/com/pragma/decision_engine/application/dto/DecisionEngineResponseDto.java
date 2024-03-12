package co.com.pragma.decision_engine.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record DecisionEngineResponseDto(

  @JsonProperty("customer")
  CustomerDto customerDto,

  @JsonProperty("financeProducts")
  List<FinanceProductDto> financeProductDtoList
) {
}
