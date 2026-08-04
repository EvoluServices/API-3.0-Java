# API-3.0-Java

SDK API-3.0 Java

## Principais recursos

- [x] Pagamentos por cartão de crédito.
- [x] Pagamentos recorrentes.
  - [x] Com autorização na primeira recorrência.
  - [x] Com autorização a partir da primeira recorrência.
- [x] Pagamentos por cartão de débito.
- [x] Pagamentos por boleto.
- [x] Pagamentos por transferência eletrônica.
- [x] Cancelamento de autorização.
- [x] Consulta de pagamentos.
- [x] Geração de token para o cartão para armazenamento seguro

## Limitações

Por envolver a interface de usuário da aplicação, o SDK funciona apenas como um framework para criação das transações. Nos casos onde a autorização é direta, não há limitação; mas nos casos onde é necessário a autenticação ou qualquer tipo de redirecionamento do usuário, o desenvolvedor deverá utilizar o SDK para gerar o pagamento e, com o link retornado pela Cielo, providenciar o redirecionamento do usuário.

## Setup (Ubuntu)

Pré-requisitos: **Java JDK 8+** e **Maven 3**.

```bash
sudo apt update
sudo apt install -y openjdk-8-jdk maven
```

Verifique a instalação:

```bash
java -version
mvn -version
```

Clone o repositório e compile o SDK:

```bash
git clone <url-do-repositorio>
cd API-3.0-Java/api30.sdk
mvn clean package
```

O artefato gerado ficará em `api30.sdk/target/api30.sdk-0.1.0.jar`.

Para usar o SDK em outro projeto Maven, instale localmente:

```bash
cd api30.sdk
mvn install
```

## Testes unitários

Os testes ficam em `api30.sdk/src/test/java` e usam JUnit 3.

Para executar todos os testes:

```bash
cd api30.sdk
mvn test
```

Para executar apenas uma classe:

```bash
cd api30.sdk
mvn -Dtest=ExternalBrandTokenTest test
```

Para executar um método específico:

```bash
cd api30.sdk
mvn -Dtest=ExternalBrandTokenTest#testPanPayloadRemainsUnchanged test
```

Se não tiver Java/Maven instalados localmente, use Docker:

```bash
docker run --rm -v "$PWD":/workspace -w /workspace/api30.sdk maven:3.9-eclipse-temurin-8 mvn test
```

## Testes de integração

Testes que chamam o **sandbox da Cielo** ficam em `api30.sdk/src/it/java` e rodam
separados dos unitários via `maven-failsafe-plugin`. Por padrão, `mvn test` e
`mvn package` **não** executam integração.

Fluxo coberto: [token de bandeira externo](https://docs.cielo.com.br/ecommerce-cielo/reference/token-bandeira-externo).

Os DPANs de teste seguem a regra do [sandbox de cartão de crédito](https://docs.cielo.com.br/ecommerce-cielo/reference/credito-sandbox): o **último dígito** define o resultado simulado. A classe usa a base `402400715376319X` (cartão de exemplo da Cielo).

| Último dígito | Cenário IT      | Status esperado | ReturnCode |
| ------------- | --------------- | --------------- | ---------- |
| 1             | Autorizado      | 1               | 4 ou 6     |
| 2             | Não autorizado  | 3               | 05         |
| 3             | Cartão expirado | 3               | 57         |

### Variáveis de ambiente

| Variável                      | Obrigatória | Descrição                                                     |
| ----------------------------- | ----------- | ------------------------------------------------------------- |
| `CIELO_MERCHANT_ID`           | Sim         | Credencial sandbox                                            |
| `CIELO_MERCHANT_KEY`          | Sim         | Credencial sandbox                                            |
| `CIELO_TEST_CRYPTOGRAM`       | Não         | Criptograma de teste (padrão: valor da documentação Cielo)    |
| `CIELO_TEST_BRAND`            | Não         | Bandeira (padrão: `Visa` na CIT, `Master` na MIT)             |
| `CIELO_ISSUER_TRANSACTION_ID` | Não         | Usado na MIT se a CIT autorizada não retornar o identificador |
| `CIELO_IT_VERBOSE`            | Não         | `true` imprime a resposta JSON completa no console            |

### Executar

```bash
cd api30.sdk

export CIELO_MERCHANT_ID="seu-merchant-id-sandbox"
export CIELO_MERCHANT_KEY="sua-merchant-key-sandbox"

mvn verify -DskipITs=false
```

A classe `ExternalBrandTokenIT` executa em ordem:

1. **CIT autorizada** — DPAN terminando em `1`, valida `Status=1` e `ReturnCode` 4 ou 6.
2. **CIT negada** — DPAN terminando em `2`, valida `Status=3` e `ReturnCode=05`.
3. **CIT cartão expirado** — DPAN terminando em `3`, valida `Status=3` e `ReturnCode=57`.
4. **MIT** — reutiliza o `IssuerTransactionId` da CIT autorizada (ou `CIELO_ISSUER_TRANSACTION_ID`) sem criptograma.

Para rodar só a integração (após compilar):

```bash
cd api30.sdk
mvn failsafe:integration-test -DskipITs=false
```

Se as credenciais não estiverem configuradas, os testes de integração são
**ignorados** automaticamente.

Para ver a resposta completa da Cielo no console:

```bash
export CIELO_IT_VERBOSE=true
mvn verify -DskipITs=false
```

## Utilizando o SDK

Para criar um pagamento simples com cartão de crédito com o SDK, basta fazer:

### Criando um pagamento com cartão de crédito

```java
// ...
// Configure seu merchant
Merchant merchant = new Merchant("MERCHANT ID", "MERCHANT KEY");

// Crie uma instância de Sale informando o ID do pagamento
Sale sale = new Sale("ID do pagamento");

// Crie uma instância de Customer informando o nome do cliente
Customer customer = sale.customer("Comprador Teste");

// Crie uma instância de Payment informando o valor do pagamento
Payment payment = sale.payment(15700);

// Crie  uma instância de Credit Card utilizando os dados de teste
// esses dados estão disponíveis no manual de integração
payment.creditCard("123", "Visa").setExpirationDate("12/2018")
                                 .setCardNumber("0000000000000001")
                                 .setHolder("Fulano de Tal");

// Crie o pagamento na Cielo
try {
    // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
    sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);

    // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
    // dados retornados pela Cielo
    String paymentId = sale.getPayment().getPaymentId();

    // Com o ID do pagamento, podemos fazer sua captura, se ela não tiver sido capturada ainda
    sale = new CieloEcommerce(merchant, Environment.SANDBOX).captureSale(paymentId, 15700, 0);

    // E também podemos fazer seu cancelamento, se for o caso
    sale = new CieloEcommerce(merchant, Environment.SANDBOX).cancelSale(paymentId, 15700);
} catch (CieloRequestException e) {
    // Em caso de erros de integração, podemos tratar o erro aqui.
    // os códigos de erro estão todos disponíveis no manual de integração.
    CieloError error = e.getError();
} catch (IOException e) {
	e.printStackTrace();
}
// ...
```

### Criando um card token para armazenamento seguro do cartão

```java
// Configure seu merchant
Merchant merchant = new Merchant("MERCHANT ID", "MERCHANT KEY");

// Informe os dados do cartão que irá tokenizar
CardToken cardToken = new CardToken().setBrand("Visa")
                                     .setCardNumber("4532117080573700")
                                     .setHolder("Comprador T Cielo")
                                     .setExpirationDate("12/2018");

// Crie o Token para o cartão
try {
	// // Configure o SDK com seu merchant e o ambiente apropriado para
	// gerar o token
	cardToken = new CieloEcommerce(merchant, Environment.SANDBOX).createCardToken(cardToken);

	String generatedToken = cardToken.getCardToken();

	System.out.printf("CardToken: %s\n", generatedToken);
} catch (CieloRequestException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
}
```

### Criando um pagamento com token de bandeira externo

O token de bandeira criado fora da Cielo deve ser informado como DPAN em
`CardNumber`. Ele não deve ser enviado em `CardToken`, pois esse campo é
reservado ao token criado pela própria Cielo.

```java
Sale sale = new Sale("ID do pagamento");
sale.customer("Comprador Teste");

Payment payment = sale.payment(15700);
payment.creditCard(null, "Visa")
       .setCardNumber("DPAN GERADO PELA BANDEIRA")
       .setCardNumberType(CreditCard.CardNumberType.DPAN)
       .setHolder("Comprador Teste")
       .setCryptogram("CRIPTOGRAMA GERADO PARA A TRANSACAO")
       .setExpirationDate("12/2030");

sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);

// Armazene sempre os identificadores mais recentes para transações relacionadas.
String issuerTransactionId = sale.getPayment().getIssuerTransactionId();
String transactionLinkId = sale.getPayment().getTransactionLinkId();
```

Quem chama o SDK é responsável por decidir se deve informar o criptograma:

- Em uma transação iniciada pelo portador (CIT), informe `Cryptogram`.
- Em uma transação Mastercard iniciada pelo estabelecimento (MIT), não informe
  `Cryptogram` e envie o `IssuerTransactionId` mais recente por meio de
  `Payment.setIssuerTransactionId`.
- `TransactionLinkId` é retornado para transações Mastercard e deve ser
  armazenado. Consulte a documentação da Cielo antes de enviá-lo em uma MIT,
  pois o contrato de envio será disponibilizado antes de sua obrigatoriedade.

`SecurityCode` é opcional no pagamento com token de bandeira. Por isso, o
exemplo passa `null` ao método `creditCard`.

## Manual

Para mais informações sobre a integração com a API 3.0 da Cielo, vide o manual em: [Integração API 3.0](https://developercielo.github.io/Webservice-3.0/)
