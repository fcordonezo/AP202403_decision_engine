package co.com.pragma.decision_engine.infrastructure.in.rest;

import co.com.pragma.decision_engine.domain.model.DecisionEngine;
import co.com.pragma.decision_engine.domain.usecase.RunUseCase;
import co.com.pragma.decision_engine.test_object.DecisionEngineDummy;
import co.com.pragma.decision_engine.test_object.TypesOfCustomers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Decision Engine Test")
class DecisionEngineControllerTest {

  @InjectMocks
  private RunUseCase runUseCase;

  private Map<TypesOfCustomers, DecisionEngine> decisionEngineMap = DecisionEngineDummy.decisionEngineMap;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  @DisplayName("Persona con todos los productos")
  void personWithAllProducts() {
    DecisionEngine decisionEngine = runUseCase.run(decisionEngineMap.get(TypesOfCustomers.WITH_ALL_PRODUCTS));
    assertNotNull(decisionEngine);
    assertEquals(decisionEngineMap.get(TypesOfCustomers.WITH_ALL_PRODUCTS).financeProductList().size(), decisionEngine.financeProductList().size());
  }

  @Test
  @DisplayName("Persona sin productos")
  void personWithNoProducts() {
    DecisionEngine decisionEngine = runUseCase.run(decisionEngineMap.get(TypesOfCustomers.WITH_NO_PRODUCTS));
    assertNotNull(decisionEngine);
    assertEquals(0, decisionEngine.financeProductList().size());
  }

  @Test
  @DisplayName("Persona de Perú (GIR)")
  void personFromPeru() {
    DecisionEngine decisionEngine = runUseCase.run(decisionEngineMap.get(TypesOfCustomers.FROM_PERU));
    assertNotNull(decisionEngine);
    assertEquals(1, decisionEngine.financeProductList().size());
    assertEquals(decisionEngine.financeProductList().get(0).code(), "GIR");
  }

  @Test
  @DisplayName("Persona con tarjeta amparada")
  void personWithTAM() {
    DecisionEngine decisionEngine = runUseCase.run(decisionEngineMap.get(TypesOfCustomers.WITH_TAM));
    assertNotNull(decisionEngine);
    assertEquals(1, decisionEngine.financeProductList().size());
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("TAM")));
  }

  @Test
  @DisplayName("Persona con 18 años y 1M (CAH, SEG, GIR, TAM)")
  void personWith18And1M() {
    DecisionEngine decisionEngine = runUseCase.run(decisionEngineMap.get(TypesOfCustomers.WITH_18_AND_1M));
    assertNotNull(decisionEngine);
    assertEquals(4, decisionEngine.financeProductList().size());
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("CAH")));
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("SEG")));
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("GIR")));
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("TAM")));
  }

  @Test
  @DisplayName("Persona con 15 años y 100K (SEG, INV, TAM)")
  void personWith15And100K() {
    DecisionEngine decisionEngine = runUseCase.run(decisionEngineMap.get(TypesOfCustomers.WITH_TAM));
    assertNotNull(decisionEngine);
    assertEquals(3, decisionEngine.financeProductList().size());
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("SEG")));
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("INV")));
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("TAM")));
  }

  @Test
  @DisplayName("Persona de Australia")
  void personFromAu() {
    DecisionEngine decisionEngine = runUseCase.run(decisionEngineMap.get(TypesOfCustomers.FROM_AU));
    assertNotNull(decisionEngine);
    assertEquals(3, decisionEngine.financeProductList().size());
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("SEG")));
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("INV")));
    assertTrue(decisionEngine.financeProductList().stream().anyMatch(product -> product.code().equals("TAM")));
  }
}