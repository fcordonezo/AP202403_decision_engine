package co.com.pragma.decision_engine.application.dto;

import lombok.Builder;

@Builder
public record CustomerDto(
  Long customerId,
  String fullName,
  Float income,
  String city,
  String countryCode,
  Integer age
) {
}
