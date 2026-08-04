package cieloecommerce.sdk.ecommerce;

import cieloecommerce.sdk.Merchant;
import cieloecommerce.sdk.ecommerce.request.CieloRequestException;
import com.google.gson.GsonBuilder;
import org.junit.Assume;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Testes de integração contra o sandbox da Cielo para token de bandeira externo.
 *
 * Usa cartões simulados conforme a documentação de sandbox da Cielo: o último
 * dígito do DPAN define autorização ou recusa.
 *
 * @see <a href="https://docs.cielo.com.br/ecommerce-cielo/reference/credito-sandbox">Cartão de crédito em sandbox</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExternalBrandTokenIT {
	private static final int STATUS_AUTHORIZED = 1;
	private static final int STATUS_DENIED = 3;

	/**
	 * Base do cartão de exemplo da Cielo ({@code 4024.0071.5376.3191}).
	 * O sandbox usa o último dígito para simular o resultado.
	 */
	private static final String SANDBOX_DPAN_PREFIX = "402400715376319";
	private static final String DEFAULT_CRYPTOGRAM = "abcdefghijklmnopqrstuvw==";
	private static final String DEFAULT_CIT_BRAND = "Visa";
	private static final String DEFAULT_MIT_BRAND = "Master";

	private static String issuerTransactionIdFromCit;

	@Test
	public void test01_citSandboxAuthorized() throws IOException, CieloRequestException {
		assumeCredentialsPresent();

		Sale sale = createCitSale(uniqueOrderId("cit-authorized"), sandboxDpan('1'));
		assertAuthorized(sale);

		issuerTransactionIdFromCit = sale.getPayment().getIssuerTransactionId();
	}

	@Test
	public void test02_citSandboxDeniedNotAuthorized() throws IOException, CieloRequestException {
		assumeCredentialsPresent();

		Sale sale = createCitSale(uniqueOrderId("cit-denied"), sandboxDpan('2'));
		assertDenied(sale, "05", "Não autorizada");
	}

	@Test
	public void test03_citSandboxDeniedExpiredCard() throws IOException, CieloRequestException {
		assumeCredentialsPresent();

		Sale sale = createCitSale(uniqueOrderId("cit-expired"), sandboxDpan('3'));
		assertDenied(sale, "57", "Cartão expirado");
	}

	@Test
	public void test04_mitExternalBrandTokenPayment() throws IOException, CieloRequestException {
		assumeCredentialsPresent();

		String issuerTransactionId = issuerTransactionIdFromCit != null
				? issuerTransactionIdFromCit
				: env("CIELO_ISSUER_TRANSACTION_ID");
		Assume.assumeTrue(
				"Requer IssuerTransactionId retornado na CIT ou CIELO_ISSUER_TRANSACTION_ID",
				issuerTransactionId != null && !issuerTransactionId.isEmpty());

		Sale sale = buildMitSale(uniqueOrderId("mit"), issuerTransactionId, sandboxDpan('1'));
		sale = createSale(sale);

		assertNotNull(sale.getPayment());
		assertNotNull(sale.getPayment().getPaymentId());
		assertNotNull(sale.getPayment().getStatus());
		logResponse("MIT", sale);
	}

	private static Sale createCitSale(String merchantOrderId, String dpan)
			throws IOException, CieloRequestException {
		Sale sale = buildCitSale(merchantOrderId, dpan);
		sale = createSale(sale);
		logResponse("CIT", sale);
		return sale;
	}

	private static Sale buildCitSale(String merchantOrderId, String dpan) {
		Sale sale = new Sale(merchantOrderId);
		sale.customer("Comprador Teste");

		sale.payment(15700)
				.creditCard(null, testBrand(DEFAULT_CIT_BRAND))
				.setCardNumber(dpan)
				.setCardNumberType(CreditCard.CardNumberType.DPAN)
				.setHolder("Comprador Teste")
				.setCryptogram(testCryptogram())
				.setExpirationDate("12/2030");

		return sale;
	}

	private static Sale buildMitSale(String merchantOrderId, String issuerTransactionId, String dpan) {
		Sale sale = new Sale(merchantOrderId);
		sale.customer("Comprador Teste");

		Payment payment = sale.payment(15700);
		payment.setIssuerTransactionId(issuerTransactionId);
		payment.setInitiatedTransactionIndicator(new InitiatedTransactionIndicator()
				.setCategory("M1")
				.setSubcategory("Subscription"));
		payment.creditCard(null, testBrand(DEFAULT_MIT_BRAND))
				.setCardNumber(dpan)
				.setCardNumberType(CreditCard.CardNumberType.DPAN)
				.setHolder("Comprador Teste")
				.setExpirationDate("12/2030");

		assertNull(payment.getCreditCard().getCryptogram());

		return sale;
	}

	private static void assertAuthorized(Sale sale) {
		Payment payment = sale.getPayment();
		assertNotNull(payment);
		assertNotNull(payment.getPaymentId());
		assertEquals(STATUS_AUTHORIZED, payment.getStatus().intValue());
		assertTrue(
				"ReturnCode esperado: 4 ou 6. Recebido: " + payment.getReturnCode(),
				"4".equals(payment.getReturnCode()) || "6".equals(payment.getReturnCode()));
	}

	private static void assertDenied(Sale sale, String expectedReturnCode, String scenario) {
		Payment payment = sale.getPayment();
		assertNotNull(payment);
		assertNotNull(payment.getPaymentId());
		assertEquals("Cenário: " + scenario, STATUS_DENIED, payment.getStatus().intValue());
		assertEquals("Cenário: " + scenario, expectedReturnCode, payment.getReturnCode());
	}

	private static String sandboxDpan(char lastDigit) {
		return SANDBOX_DPAN_PREFIX + lastDigit;
	}

	private static Sale createSale(Sale sale) throws IOException, CieloRequestException {
		Merchant merchant = new Merchant(env("CIELO_MERCHANT_ID"), env("CIELO_MERCHANT_KEY"));
		return new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);
	}

	private static void assumeCredentialsPresent() {
		String merchantId = env("CIELO_MERCHANT_ID");
		String merchantKey = env("CIELO_MERCHANT_KEY");
		Assume.assumeTrue(
				"CIELO_MERCHANT_ID e CIELO_MERCHANT_KEY são obrigatórios para testes de integração",
				merchantId != null && !merchantId.isEmpty()
						&& merchantKey != null && !merchantKey.isEmpty());
	}

	private static String testCryptogram() {
		return envOrDefault("CIELO_TEST_CRYPTOGRAM", DEFAULT_CRYPTOGRAM);
	}

	private static String testBrand(String defaultBrand) {
		return envOrDefault("CIELO_TEST_BRAND", defaultBrand);
	}

	private static String uniqueOrderId(String prefix) {
		return "it-" + prefix + "-" + UUID.randomUUID().toString().substring(0, 8);
	}

	private static void logResponse(String label, Sale sale) {
		if (!"true".equalsIgnoreCase(env("CIELO_IT_VERBOSE"))) {
			return;
		}

		System.out.println(label + " response:");
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(sale));
	}

	private static String env(String name) {
		return System.getenv(name);
	}

	private static String envOrDefault(String name, String defaultValue) {
		String value = env(name);
		if (value == null || value.isEmpty()) {
			return defaultValue;
		}
		return value;
	}
}
