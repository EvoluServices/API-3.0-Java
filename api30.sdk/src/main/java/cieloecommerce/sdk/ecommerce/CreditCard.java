package cieloecommerce.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;

public class CreditCard {
	@SerializedName("CardNumber")
	private String cardNumber;

	@SerializedName("CardNumberType")
	private CardNumberType cardNumberType;

	@SerializedName("Holder")
	private String holder;

	@SerializedName("Cryptogram")
	private String cryptogram;

	@SerializedName("ExpirationDate")
	private String expirationDate;

	@SerializedName("SecurityCode")
	private String securityCode;

	@SerializedName("SaveCard")
	private boolean saveCard = false;

	@SerializedName("Brand")
	private String brand;

	@SerializedName("CardToken")
	private String cardToken;

	public CreditCard(String securityCode, String brand) {
		setSecurityCode(securityCode);
		setBrand(brand);
	}

	public String getBrand() {
		return brand;
	}

	public CreditCard setBrand(String brand) {
		this.brand = brand;
		return this;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public CreditCard setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
		return this;
	}

	public CardNumberType getCardNumberType() {
		return cardNumberType;
	}

	public CreditCard setCardNumberType(CardNumberType cardNumberType) {
		this.cardNumberType = cardNumberType;
		return this;
	}

	public String getCryptogram() {
		return cryptogram;
	}

	public CreditCard setCryptogram(String cryptogram) {
		this.cryptogram = cryptogram;
		return this;
	}

	public String getCardToken() {
		return cardToken;
	}

	public CreditCard setCardToken(String cardToken) {
		this.cardToken = cardToken;
		return this;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public CreditCard setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
		return this;
	}

	public String getHolder() {
		return holder;
	}

	public CreditCard setHolder(String holder) {
		this.holder = holder;
		return this;
	}

	public boolean isSaveCard() {
		return saveCard;
	}

	public CreditCard setSaveCard(boolean saveCard) {
		this.saveCard = saveCard;
		return this;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public CreditCard setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
		return this;
	}

	public enum CardNumberType {
		PAN, DPAN
	}
}
