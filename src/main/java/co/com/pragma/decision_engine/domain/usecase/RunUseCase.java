package co.com.pragma.decision_engine.domain.usecase;

import co.com.pragma.decision_engine.domain.api.RunServicePort;
import co.com.pragma.decision_engine.domain.model.Customer;
import co.com.pragma.decision_engine.domain.model.DecisionEngine;
import co.com.pragma.decision_engine.domain.model.FinanceService;
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
    List<FinanceService> financeServiceList = decisionEngine.financeServiceList();

    List<FinanceService> newFinanceService = financeServiceList.stream()
      .filter(financeService -> this.applyRuleSet(financeService, customer)).toList();
    return new DecisionEngine(customer, newFinanceService);
  }

  private Boolean applyRuleSet(FinanceService financeService, Customer customer) {
    try {
      Map<?, ?> ruleSet = mapper.readValue(financeService.ruleSet(), Map.class);
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
