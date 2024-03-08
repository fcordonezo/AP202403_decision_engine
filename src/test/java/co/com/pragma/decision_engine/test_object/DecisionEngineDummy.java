package co.com.pragma.decision_engine.test_object;

import co.com.pragma.decision_engine.domain.model.Customer;
import co.com.pragma.decision_engine.domain.model.DecisionEngine;
import co.com.pragma.decision_engine.domain.model.FinanceProduct;

import java.util.List;
import java.util.Map;

public class DecisionEngineDummy {
  private static List<FinanceProduct> financeProducts = List.of(
    FinanceProduct.builder()
      .financeProductId(1L)
      .code("CAH")
      .description("Cuenta de ahorros")
      .ruleSet("{\"countries\": [\"CO\"], \"minAge\": 18,\"minIncome\": 0.0}")
      .build(),
    FinanceProduct.builder()
      .financeProductId(2L)
      .code("TDB")
      .description("Tarjeta débito")
      .ruleSet("{\"countries\": [\"CO\"], \"minAge\": 18,\"minIncome\": 1300000.0}")
      .build(),
    FinanceProduct.builder()
      .financeProductId(3L)
      .code("TDC")
      .description("Tarjeta de crédito")
      .ruleSet("{\"countries\": [\"CO\"], \"minAge\": 20,\"minIncome\": 2500000.0}")
      .build(),
    FinanceProduct.builder()
      .financeProductId(4L)
      .code("SEG")
      .description("Seguro")
      .ruleSet("{\"countries\": [], \"minAge\": 15,\"minIncome\": 800000.0}")
      .build(),
    FinanceProduct.builder()
      .financeProductId(5L)
      .code("INV")
      .description("Inversión")
      .ruleSet("{\"countries\": [], \"minAge\": 25,\"minIncome\": 4500000.0}")
      .build(),
    FinanceProduct.builder()
      .financeProductId(6L)
      .code("GIR")
      .description("Giro")
      .ruleSet("{\"countries\": [\"CO\", \"PE\", \"EC\", \"PA\"], \"minAge\": 0,\"minIncome\": 0.0}")
      .build(),
    FinanceProduct.builder()
      .financeProductId(7L)
      .code("TAM")
      .description("Tarjeta amparada")
      .ruleSet("{\"countries\": [], \"minAge\": 15,\"minIncome\": 0.0}")
      .build()
  );

  private static Map<TypesOfCustomers, Customer> customerMap = Map.of(
    TypesOfCustomers.WITH_ALL_PRODUCTS,
    Customer.builder()
      .customerId(1L)
      .age(40)
      .city("Bogotá")
      .countryCode("CO")
      .fullName("Persona con todos los productos")
      .income(20000000.0F)
      .build(),
    TypesOfCustomers.WITH_NO_PRODUCTS,
    Customer.builder()
      .customerId(2L)
      .age(10)
      .city("Bogotá")
      .countryCode("AR")
      .fullName("Persona sin productos")
      .income(0.0F)
      .build(),
    TypesOfCustomers.FROM_PERU,
    Customer.builder()
      .customerId(3L)
      .age(35)
      .city("Bogotá")
      .countryCode("PE")
      .fullName("Persona de Perú")
      .income(30000000.0F)
      .build(),
    TypesOfCustomers.WITH_TAM,
    Customer.builder()
      .customerId(4L)
      .age(15)
      .city("Bogotá")
      .countryCode("CO")
      .fullName("Persona con tarjeta amparada")
      .income(0.0F)
      .build(),
    TypesOfCustomers.WITH_18_AND_1M,
    Customer.builder()
      .customerId(5L)
      .age(18)
      .city("Bogotá")
      .countryCode("CO")
      .fullName("Persona con 18 años y 1M")
      .income(1000000.0F)
      .build(),
    TypesOfCustomers.WITH_15_AND_100K,
    Customer.builder()
      .customerId(6L)
      .age(15)
      .city("Bogotá")
      .countryCode("CO")
      .fullName("Persona con 15 años y 100K")
      .income(100000.0F)
      .build(),
    TypesOfCustomers.FROM_AU,
    Customer.builder()
      .customerId(7L)
      .age(37)
      .city("Bogotá")
      .countryCode("AU")
      .fullName("Persona de Australia")
      .income(20000000.0F)
      .build()
  );

  public static Map<TypesOfCustomers, DecisionEngine> decisionEngineMap = Map.of(
    TypesOfCustomers.WITH_ALL_PRODUCTS,
    DecisionEngine.builder().customer(customerMap.get(TypesOfCustomers.WITH_ALL_PRODUCTS)).financeProductList(financeProducts).build(),
    TypesOfCustomers.WITH_NO_PRODUCTS,
    DecisionEngine.builder().customer(customerMap.get(TypesOfCustomers.WITH_NO_PRODUCTS)).financeProductList(financeProducts).build(),
    TypesOfCustomers.FROM_PERU,
    DecisionEngine.builder().customer(customerMap.get(TypesOfCustomers.FROM_PERU)).financeProductList(financeProducts).build(),
    TypesOfCustomers.WITH_TAM,
    DecisionEngine.builder().customer(customerMap.get(TypesOfCustomers.WITH_TAM)).financeProductList(financeProducts).build(),
    TypesOfCustomers.WITH_18_AND_1M,
    DecisionEngine.builder().customer(customerMap.get(TypesOfCustomers.WITH_18_AND_1M)).financeProductList(financeProducts).build(),
    TypesOfCustomers.WITH_15_AND_100K,
    DecisionEngine.builder().customer(customerMap.get(TypesOfCustomers.WITH_15_AND_100K)).financeProductList(financeProducts).build(),
    TypesOfCustomers.FROM_AU,
    DecisionEngine.builder().customer(customerMap.get(TypesOfCustomers.FROM_AU)).financeProductList(financeProducts).build()
  );

}
