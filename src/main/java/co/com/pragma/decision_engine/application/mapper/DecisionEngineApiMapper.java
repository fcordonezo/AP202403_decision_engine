package co.com.pragma.decision_engine.application.mapper;

import co.com.pragma.decision_engine.application.dto.CustomerDto;
import co.com.pragma.decision_engine.application.dto.DecisionEngineRequestDto;
import co.com.pragma.decision_engine.application.dto.DecisionEngineResponseDto;
import co.com.pragma.decision_engine.application.dto.FinanceProductDto;
import co.com.pragma.decision_engine.domain.model.Customer;
import co.com.pragma.decision_engine.domain.model.DecisionEngine;
import co.com.pragma.decision_engine.domain.model.FinanceProduct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DecisionEngineApiMapper {

  DecisionEngineApiMapper INSTANCE = Mappers.getMapper(DecisionEngineApiMapper.class);

  Customer toCustomer(CustomerDto dto);
  CustomerDto toCustomerDto(Customer customer);
  FinanceProduct toFinanceService(FinanceProductDto dto);
  FinanceProductDto toFinanceServiceDto(FinanceProduct financeProduct);


  default DecisionEngine toDecisionEngine(DecisionEngineRequestDto dto) {
    if (dto == null){
      return null;
    }
    Customer customer = INSTANCE.toCustomer(dto.customerDto());
    List<FinanceProduct> financeProductList = dto.financeProductDtoList().stream().map(INSTANCE::toFinanceService).toList();
    return new DecisionEngine(customer, financeProductList);
  }
  default DecisionEngineResponseDto toDecisionEngineResponseDto(DecisionEngine decisionEngine) {
    if (decisionEngine == null){
      return null;
    }
    CustomerDto customerDto = INSTANCE.toCustomerDto(decisionEngine.customer());
    List<FinanceProductDto> financeProductDtoList = decisionEngine.financeProductList().stream().map(INSTANCE::toFinanceServiceDto).toList();
    return new DecisionEngineResponseDto(customerDto, financeProductDtoList);
  }
}
