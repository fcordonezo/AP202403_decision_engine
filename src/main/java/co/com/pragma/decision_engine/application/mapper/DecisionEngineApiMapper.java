package co.com.pragma.decision_engine.application.mapper;

import co.com.pragma.decision_engine.application.dto.CustomerDto;
import co.com.pragma.decision_engine.application.dto.DecisionEngineRequestDto;
import co.com.pragma.decision_engine.application.dto.DecisionEngineResponseDto;
import co.com.pragma.decision_engine.application.dto.FinanceServiceDto;
import co.com.pragma.decision_engine.domain.model.Customer;
import co.com.pragma.decision_engine.domain.model.DecisionEngine;
import co.com.pragma.decision_engine.domain.model.FinanceService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DecisionEngineApiMapper {

  DecisionEngineApiMapper INSTANCE = Mappers.getMapper(DecisionEngineApiMapper.class);

  Customer toCustomer(CustomerDto dto);
  CustomerDto toCustomerDto(Customer customer);
  FinanceService toFinanceService(FinanceServiceDto dto);
  FinanceServiceDto toFinanceServiceDto(FinanceService financeService);


  default DecisionEngine toDecisionEngine(DecisionEngineRequestDto dto) {
    if (dto == null){
      return null;
    }
    Customer customer = INSTANCE.toCustomer(dto.customerDto());
    List<FinanceService> financeServiceList = dto.financeServiceDtoList().stream().map(INSTANCE::toFinanceService).toList();
    return new DecisionEngine(customer, financeServiceList);
  }
  default DecisionEngineResponseDto toDecisionEngineResponseDto(DecisionEngine decisionEngine) {
    if (decisionEngine == null){
      return null;
    }
    CustomerDto customerDto = INSTANCE.toCustomerDto(decisionEngine.customer());
    List<FinanceServiceDto> financeServiceDtoList = decisionEngine.financeServiceList().stream().map(INSTANCE::toFinanceServiceDto).toList();
    return new DecisionEngineResponseDto(customerDto, financeServiceDtoList);
  }
}
