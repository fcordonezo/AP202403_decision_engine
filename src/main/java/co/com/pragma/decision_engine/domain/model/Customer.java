package co.com.pragma.decision_engine.domain.model;

import lombok.Builder;

@Builder
public record Customer (
  Long customerId,
  String fullName,
  Float income,
  String city,
  String countryCode,
  Integer age
){
}
