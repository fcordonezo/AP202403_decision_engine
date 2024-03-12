# Proyecto Assesment Pragma 2024/03: Motor de Decisiones

## Descripción general

El **microservicio** llamado ***Motor de Decisiones (decision-engine)*** es el encargado de,
dado un *cliente* y un conjunto de *productos financieros*, realizar el proceso de filtrado
de productos financieros a través del análisis de cada una de sus ```ruleSet```, retornando así
el mismo *cliente* y la lista de *productos financieros* ya filtrada.

### Host: localhost
### Port: 8082
### Path: /api/development/v1/finance-service/decision-engine

## Aspectos técnicos

### Lenguaje y framework
Este es un servicio desarrollado en **Java 21** con **Spring Boot 3.2.3** y
gestor de dependencias **Maven**.

### Seguridad
Se realizó una robusta implementación de seguridad a través del uso de **LWT** a través de la librería
*Spring Security* y utilizando cifrado asimétrico **RSA PKCS#8**. La llave pública se dispone en este
servicio, mientras que la clave privada se deja en el servicio consumidor (Postman para pruebas).
El algoritmo utilizado para la generación del JWT fue **RS256**

Para configurar el JWT en Postman se deben seguir los siguientes pasos:
en la pestaña de *Authorization* se debe seleccionar, en *Type* ```JWT Bearer```. Una vez allí,
los campos que salen se deben establecer de la siguiente manera:
* Algorithm: RS256
* Private key: Copiar y pegar todo el contenido de la llave privada, la cual se encuentra en la ruta
  *src/main/java/resources/cert/private_key.pem*
* Payload: Se debe configurar el siguiente json:
    ``` json
    {
      "sub": "decision-engine",
      "name": "finance-service",
      "iat": {{$timestamp}}
    }
    ```
* Request header prefix: Bearer
* JWT headers: un json vacío ```{}```

## Ejecución

Hay dos opciones para poder lanzar el proyecto de forma local:
1. Usando el comando mvn, para lo cual hay que tener instalado Maven así como el SDK de Java 21
   en el computador, y también se deben configurar las variables de entorno. Después de tener estas
   configuraciones, simplemente se debe abrir un terminal, posicionarse en la raíz del proyecto
   y escribir los siguientes comandos
    ``` shell
    mvn clean install
    mvn spring-boot:run
   ```
2. Ejecutando el proyecto desde IntelliJ Idea, para lo cual lo único necesario es configurar la
   versión de Java 21 desde la configuración *File → Project Structure → SDK*, y luego seleccionar
   la clase main desde la opción de *Aplication* en la configuración *run/debug configuration*
   o, en su defecto, correr los comandos *Maven* desde el panel derecho. Los comandos son
   ```clean```, ```compile``` y ```spring-boot```

*Por defecto se ejecuta en el puerto 8082, por lo que se debe tener en cuenta el no tener
ninguna otra aplicación ejecutándose en este puerto.*

## Definición funcional

### Objeto de negocio

Se realizó un completo análisis del objeto de negocio y el resultado se plasma en el siguiente Json:
```json
{
  "customer": {
    "customerId": "int",
    "fullName": "string",
    "income": "float",
    "city": "string",
    "countryCode": "string",
    "age": "int"
  },
  "financeProducts": [
    {
      "financeProductId": "int",
      "code": "string",
      "description": "string",
      "ruleSet": "string"
    }
  ]
}
```
Como se puede notar, el objeto de negocio se compone tanto del objeto ```customer``` como de un arreglo
de objetos ```financeProduct```.

```customer```: Objeto de negocio *cliente*. Más información sobre este objeto de negocio en
el [siguiente enlace]().  
```financeProducts```: Lista de objetos de negocio *producto financiero*.
Más información sobre este objeto de negocio en el [siguiente enlace]().

### Ejemplo
```json
{
  "customer": {
    "customerId": 1,
    "fullName": "Alberto Estrada",
    "income": 1200000.0,
    "city": "Bogotá",
    "countryCode": "CO",
    "age": 30
  },
  "financeProducts": [
    {
      "financeProductId": 1,
      "code": "CAH",
      "description": "Cuenta de ahorros",
      "ruleSet": "{\"countries\": [\"CO\"], \"minAge\": 18,\"minIncome\": 0}"
    },
    {
      "financeProductId": 2,
      "code": "TDB",
      "description": "Tarjeta débito",
      "ruleSet": "{\"countries\": [\"CO\"], \"minAge\": 18,\"minIncome\": 1300000}"
    },
    {
      "financeProductId": 3,
      "code": "TDC",
      "description": "Tarjeta de crédito",
      "ruleSet": "{\"countries\": [\"CO\"], \"minAge\": 20,\"minIncome\": 2500000}"
    },
    {
      "financeProductId": 4,
      "code": "SEG",
      "description": "Seguro",
      "ruleSet": "{\"countries\": [], \"minAge\": 15,\"minIncome\": 800000}"
    },
    {
      "financeProductId": 5,
      "code": "INV",
      "description": "Inversión",
      "ruleSet": "{\"countries\": [], \"minAge\": 25,\"minIncome\": 4500000}"
    },
    {
      "financeProductId": 6,
      "code": "GIR",
      "description": "Giro",
      "ruleSet": "{\"countries\": [\"CO\", \"PE\", \"EC\", \"PA\"], \"minAge\": 0,\"minIncome\": 0.0}"
    },
    {
      "financeProductId": 7,
      "code": "TAM",
      "description": "Tarjeta amparada",
      "ruleSet": "{\"countries\": [], \"minAge\": 15,\"minIncome\": 0}"
    }
  ]
}
```

### Contrato Openapi 3.0.1
La firma puede ser encontrada en **[esta página](https://fcordonezo.github.io/AP202403_decision_engine/)**
``` yaml
openapi: 3.0.1
info:
  title: Motor de decisiones
  version: 1.0.0
servers:
- url: http://127.0.0.1:8082/api/development/v1/finance-service/decision-engine
  description: Generated server url
paths:
  /run:
    post:
      tags:
      - decision-engine-controller
      summary: Ejecutar motor de decisiones
      operationId: run
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/DecisionEngineRequestDto'
        required: true
      responses:
        "200":
          description: OK
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/DecisionEngineResponseDto'
      security:
      - BearerAuthentication: []
components:
  schemas:
    CustomerDto:
      type: object
      properties:
        customerId:
          type: integer
          format: int64
        fullName:
          type: string
        income:
          type: number
          format: float
        city:
          type: string
        countryCode:
          type: string
        age:
          type: integer
          format: int32
    DecisionEngineRequestDto:
      type: object
      properties:
        customerDto:
          $ref: '#/components/schemas/CustomerDto'
        financeProductDtoList:
          type: array
          items:
            $ref: '#/components/schemas/FinanceProductDto'
    FinanceProductDto:
      type: object
      properties:
        financeProductId:
          type: integer
          format: int64
        code:
          type: string
        description:
          type: string
        ruleSet:
          type: string
    DecisionEngineResponseDto:
      type: object
      properties:
        customer:
          $ref: '#/components/schemas/CustomerDto'
        financeProducts:
          type: array
          items:
            $ref: '#/components/schemas/FinanceProductDto'
  securitySchemes:
    BearerAuthentication:
      type: http
      scheme: bearer
      bearerFormat: JWT
```

## Documentación de referencia

Puede encontrar el repositorio de documentación en el [siguiente enlace]()

### Documentación oficial

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.3/maven-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#web)
* [Spring Security](https://spring.io/projects/spring-security)

### Guías

* [Construyendo un servicio RESTful](https://spring.io/guides/gs/rest-service/)
* [Construyendo servicios REST con Spring](https://spring.io/guides/tutorials/rest/)
* [Intro to JWT](https://jwt.io/introduction)

