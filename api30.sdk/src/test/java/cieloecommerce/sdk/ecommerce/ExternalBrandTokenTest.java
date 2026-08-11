package cieloecommerce.sdk.ecommerce;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import junit.framework.TestCase;

public class ExternalBrandTokenTest extends TestCase {
	private final Gson gson = new Gson();

	public void testPanPayloadRemainsUnchanged() {
		Sale sale = new Sale("order-pan");
		sale.payment(15700)
				.creditCard("123", "Visa")
				.setCardNumber("0000000000000001")
				.setHolder("Comprador Teste")
				.setExpirationDate("12/2030");

		assertEquals(json("{"
				+ "\"MerchantOrderId\":\"order-pan\","
				+ "\"Payment\":{"
				+ "\"Installments\":1,"
				+ "\"Capture\":false,"
				+ "\"Authenticate\":false,"
				+ "\"Recurrent\":false,"
				+ "\"CreditCard\":{"
				+ "\"CardNumber\":\"0000000000000001\","
				+ "\"Holder\":\"Comprador Teste\","
				+ "\"ExpirationDate\":\"12/2030\","
				+ "\"SecurityCode\":\"123\","
				+ "\"SaveCard\":false,"
				+ "\"Brand\":\"Visa\"},"
				+ "\"SoftDescriptor\":\"\","
				+ "\"Type\":\"CreditCard\","
				+ "\"Amount\":15700}}"), json(gson.toJson(sale)));
	}

	public void testCieloCardTokenPayloadRemainsUnchanged() {
		Sale sale = new Sale("order-card-token");
		sale.payment(15700)
				.creditCard("123", "Visa")
				.setCardToken("d37bf475-307d-47be-b50a-8dcc38c5056c");

		JsonElement payload = json(gson.toJson(sale));
		JsonElement creditCard = payload.getAsJsonObject()
				.getAsJsonObject("Payment")
				.getAsJsonObject("CreditCard");

		assertEquals("d37bf475-307d-47be-b50a-8dcc38c5056c",
				creditCard.getAsJsonObject().get("CardToken").getAsString());
		assertFalse(creditCard.getAsJsonObject().has("CardNumberType"));
		assertFalse(creditCard.getAsJsonObject().has("Cryptogram"));
	}

	public void testCitExternalTokenPayloadContainsDpanAndCryptogram() {
		Sale sale = new Sale("order-cit");
		sale.payment(15700)
				.creditCard(null, "Visa")
				.setCardNumber("1234123412341231")
				.setCardNumberType(CreditCard.CardNumberType.DPAN)
				.setHolder("Comprador Teste")
				.setCryptogram("abcdefghijklmnopqrstuvw==")
				.setExpirationDate("12/2030");

		JsonElement creditCard = json(gson.toJson(sale)).getAsJsonObject()
				.getAsJsonObject("Payment")
				.getAsJsonObject("CreditCard");

		assertEquals("1234123412341231", creditCard.getAsJsonObject().get("CardNumber").getAsString());
		assertEquals("DPAN", creditCard.getAsJsonObject().get("CardNumberType").getAsString());
		assertEquals("abcdefghijklmnopqrstuvw==",
				creditCard.getAsJsonObject().get("Cryptogram").getAsString());
		assertFalse(creditCard.getAsJsonObject().has("SecurityCode"));
		assertFalse(creditCard.getAsJsonObject().has("CardToken"));
	}

	public void testMastercardMitPayloadOmitsCryptogramAndSendsIssuerTransactionId() {
		Sale sale = new Sale("order-mit");
		Payment payment = sale.payment(15700);
		payment.setIssuerTransactionId("580027442382078");
		payment.setInitiatedTransactionIndicator(new InitiatedTransactionIndicator()
				.setCategory("M1")
				.setSubcategory("Subscription"));
		payment.creditCard(null, "Master")
				.setCardNumber("1234123412341231")
				.setCardNumberType(CreditCard.CardNumberType.DPAN)
				.setHolder("Comprador Teste")
				.setExpirationDate("12/2030");

		JsonElement paymentPayload = json(gson.toJson(sale)).getAsJsonObject()
				.getAsJsonObject("Payment");
		JsonElement creditCard = paymentPayload.getAsJsonObject().getAsJsonObject("CreditCard");

		assertEquals("580027442382078",
				paymentPayload.getAsJsonObject().get("IssuerTransactionId").getAsString());
		assertFalse(creditCard.getAsJsonObject().has("Cryptogram"));
		assertFalse(paymentPayload.getAsJsonObject().has("TransactionLinkId"));
	}

	public void testBrandIdentifiersAreDeserializedFromResponse() {
		Sale sale = gson.fromJson("{\"Payment\":{"
				+ "\"IssuerTransactionId\":\"580027442382078\","
				+ "\"TransactionLinkId\":\"mK8vT2qA-9LpX7dWc3Rf_HsD1Z\"}}", Sale.class);

		assertEquals("580027442382078", sale.getPayment().getIssuerTransactionId());
		assertEquals("mK8vT2qA-9LpX7dWc3Rf_HsD1Z", sale.getPayment().getTransactionLinkId());
	}

	private JsonElement json(String value) {
		return new JsonParser().parse(value);
	}
}
