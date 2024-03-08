package co.com.pragma.decision_engine.domain.usecase;

import co.com.pragma.decision_engine.domain.api.RunServicePort;
import co.com.pragma.decision_engine.domain.model.Customer;
import co.com.pragma.decision_engine.domain.model.DecisionEngine;
import co.com.pragma.decision_engine.domain.model.FinanceProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RunUseCase implements RunServicePort {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public DecisionEngine run(DecisionEngine decisionEngine) {

    Customer customer = decisionEngine.customer();
    List<FinanceProduct> financeProductList = decisionEngine.financeProductList();

    List<FinanceProduct> newFinanceProduct = financeProductList.stream()
      .filter(financeProduct -> this.applyRuleSet(financeProduct, customer)).toList();
    return new DecisionEngine(customer, newFinanceProduct);
  }

  private Boolean applyRuleSet(FinanceProduct financeProduct, Customer customer) {
    try {
      Map<?, ?> ruleSet = mapper.readValue(financeProduct.ruleSet(), Map.class);
      if(customer.age() < (Integer)ruleSet.get("minAge")) {
        return false;
      }
      if(customer.income() < Double.parseDouble((ruleSet.get("minIncome").toString()))) {
        return false;
      }
      List<String> countries = new ArrayList<>((List<String>)ruleSet.get("countries"));
      if(countries.stream().noneMatch(s -> s.equals(customer.countryCode()))) {
        return false;
      }
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return true;
  }
}
