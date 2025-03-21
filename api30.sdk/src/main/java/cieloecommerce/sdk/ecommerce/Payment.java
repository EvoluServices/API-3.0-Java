package cieloecommerce.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;

public class Payment {
	@SerializedName("ServiceTaxAmount")
	private Integer serviceTaxAmount;
	@SerializedName("Installments")
	private Integer installments;
	@SerializedName("Interest")
	private String interest;
	@SerializedName("Capture")
	private boolean capture = false;
	@SerializedName("Authenticate")
	private boolean authenticate = false;
	@SerializedName("Recurrent")
	private boolean recurrent;
	@SerializedName("RecurrentPayment")
	private RecurrentPayment recurrentPayment;
	@SerializedName("CreditCard")
	private CreditCard creditCard;
	@SerializedName("DebitCard")
	private DebitCard debitCard;
	@SerializedName("Tid")
	private String tid;
	@SerializedName("ProofOfSale")
	private String proofOfSale;
	@SerializedName("AuthorizationCode")
	private String authorizationCode;
	@SerializedName("SoftDescriptor")
	private String softDescriptor = "";
	@SerializedName("PaymentFacilitator")
	private PaymentFacilitator paymentFacilitator;
	@SerializedName("ReturnUrl")
	private String returnUrl;
	@SerializedName("Provider")
	private Provider provider;
	@SerializedName("PaymentId")
	private String paymentId;
	@SerializedName("Type")
	private Type type;
	@SerializedName("Amount")
	private Integer amount;
	@SerializedName("ReceivedDate")
	private String receivedDate;
	@SerializedName("CapturedAmount")
	private Integer capturedAmount;
	@SerializedName("CapturedDate")
	private String capturedDate;
	@SerializedName("Currency")
	private Currency currency;
	@SerializedName("Country")
	private String country;
	@SerializedName("ReturnCode")
	private String returnCode;
	@SerializedName("ReturnMessage")
	private String returnMessage;
	@SerializedName("Status")
	private Integer status;
	@SerializedName("Links")
	private Object links[];
	@SerializedName("ExtraDataCollection")
	private Object extraDataCollection[];
	@SerializedName("ExpirationDate")
	private String expirationDate;
	@SerializedName("Url")
	private String url;
	@SerializedName("Number")
	private String number;
	@SerializedName("BarCodeNumber")
	private String barCodeNumber;
	@SerializedName("DigitableLine")
	private String digitableLine;
	@SerializedName("Address")
	private String address;
	@SerializedName("BoletoNumber")
	private String boletoNumber;
	@SerializedName("Assignor")
	private String assignor;
	@SerializedName("Demonstrative")
	private String demonstrative;
	@SerializedName("Identification")
	private String identification;
	@SerializedName("Instructions")
	private String instructions;
	@SerializedName("AuthenticationUrl")
	private String authenticationUrl;
	@SerializedName("VoidedAmount")
	private Integer voidedAmount;
	@SerializedName("VoidedDate")
	private String voidedDate;
	@SerializedName("QrCodeBase64Image")
	private String qrCodeBase64Image;
	@SerializedName("ExternalAuthentication")
	private ExternalAuthentication externalAuthentication;
	@SerializedName("InitiatedTransactionIndicator")
	private InitiatedTransactionIndicator initiatedTransactionIndicator;

	public Payment(Integer amount, Integer installments) {
		setAmount(amount);
		setInstallments(installments);
	}

	public Payment(Integer amount) {
		this(amount, 1);
	}

	public CreditCard creditCard(String securityCode, String brand) {
		setType(Type.CreditCard);
		setCreditCard(new CreditCard(securityCode, brand));

		return getCreditCard();
	}

	public DebitCard debitCard(String securityCode, String brand) {
		setType(Type.DebitCard);
		setDebitCard(new DebitCard(securityCode, brand));

		return getDebitCard();
	}


	public RecurrentPayment recurrentPayment(boolean authorizeNow) {
		setRecurrentPayment(new RecurrentPayment(authorizeNow));

		return getRecurrentPayment();
	}

	public Integer getAmount() {
		return amount;
	}

	public Payment setAmount(Integer amount) {
		this.amount = amount;
		return this;
	}

	public boolean getAuthenticate() {
		return authenticate;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public String getAuthenticationUrl() {
		return authenticationUrl;
	}

	public Payment setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
		return this;
	}

	public boolean getCapture() {
		return capture;
	}

	public Integer getCapturedAmount() {
		return capturedAmount;
	}

	public Payment setCapturedAmount(Integer capturedAmount) {
		this.capturedAmount = capturedAmount;
		return this;
	}

	public String getCapturedDate() {
		return capturedDate;
	}

	public Payment setCapturedDate(String capturedDate) {
		this.capturedDate = capturedDate;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public Payment setCountry(String country) {
		this.country = country;
		return this;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public Payment setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
		return this;
	}

	public Payment setDebitCard(DebitCard debitCard) {
		this.debitCard = debitCard;
		return this;
	}

	public DebitCard getDebitCard() {
		return debitCard;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Payment setCurrency(Currency currency) {
		this.currency = currency;
		return this;
	}

	public Integer getInstallments() {
		return installments;
	}

	public Payment setInstallments(Integer installments) {
		this.installments = installments;
		return this;
	}

	public String getInterest() {
		return interest;
	}

	public Payment setInterest(String interest) {
		this.interest = interest;
		return this;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public Payment setPaymentId(String paymentId) {
		this.paymentId = paymentId;
		return this;
	}

	public String getProofOfSale() {
		return proofOfSale;
	}

	public Payment setProofOfSale(String proofOfSale) {
		this.proofOfSale = proofOfSale;
		return this;
	}

	public Provider getProvider() {
		return provider;
	}

	public Payment setProvider(Provider provider) {
		this.provider = provider;
		return this;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public Payment setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
		return this;
	}

	public boolean isRecurrent() {
		return recurrent;
	}

	public Payment setRecurrent(boolean recurrent) {
		this.recurrent = recurrent;
		return this;
	}

	public RecurrentPayment getRecurrentPayment() {
		return recurrentPayment;
	}

	public Payment setRecurrentPayment(RecurrentPayment recurrentPayment) {
		this.recurrentPayment = recurrentPayment;
		return this;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public Payment setReturnCode(String returnCode) {
		this.returnCode = returnCode;
		return this;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public Payment setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
		return this;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public Payment setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
		return this;
	}

	public Integer getServiceTaxAmount() {
		return serviceTaxAmount;
	}

	public Payment setServiceTaxAmount(Integer serviceTaxAmount) {
		this.serviceTaxAmount = serviceTaxAmount;
		return this;
	}

	public String getSoftDescriptor() {
		return softDescriptor;
	}

	public Payment setSoftDescriptor(String softDescriptor) {
		this.softDescriptor = softDescriptor;
		return this;
	}

	public PaymentFacilitator getPaymentFacilitator() { return paymentFacilitator; }

	public Payment setPaymentFacilitator(PaymentFacilitator paymentFacilitator) {
		this.paymentFacilitator = paymentFacilitator;
		return this;
	}

	public Integer getStatus() {
		return status;
	}

	public Payment setStatus(Integer status) {
		this.status = status;
		return this;
	}

	public String getTid() {
		return tid;
	}

	public Payment setTid(String tid) {
		this.tid = tid;
		return this;
	}

	public Type getType() {
		return type;
	}

	public Payment setType(Type type) {
		this.type = type;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public boolean isAuthenticate() {
		return authenticate;
	}

	public Payment setAuthenticate(boolean authenticate) {
		this.authenticate = authenticate;
		return this;
	}

	public String getBarCodeNumber() {
		return barCodeNumber;
	}

	public boolean isCapture() {
		return capture;
	}

	public Payment setCapture(boolean capture) {
		this.capture = capture;
		return this;
	}

	public String getBoletoNumber() {
		return boletoNumber;
	}

	public Payment setBoletoNumber(String boletoNumber) {
		this.boletoNumber = boletoNumber;
		return this;
	}

	public String getDemonstrative() {
		return demonstrative;
	}

	public Payment setDemonstrative(String demonstrative) {
		this.demonstrative = demonstrative;
		return this;
	}

	public String getIdentification() {
		return identification;
	}

	public Payment setIdentification(String identification) {
		this.identification = identification;
		return this;
	}

	public String getInstructions() {
		return instructions;
	}

	public Payment setInstructions(String instructions) {
		this.instructions = instructions;
		return this;
	}

	public String getDigitableLine() {
		return digitableLine;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public Object[] getExtraDataCollection() {
		return extraDataCollection;
	}

	public Object[] getLinks() {
		return links;
	}

	public String getNumber() {
		return number;
	}

	public String getUrl() {
		return url;
	}

	public Integer getVoidedAmount() {
		return voidedAmount;
	}

	public String getVoidedDate() {
		return voidedDate;
	}

	public String getQrCodeBase64Image() {
		return qrCodeBase64Image;
	}

	public void setQrCodeBase64Image(String qrCodeBase64Image) {
		this.qrCodeBase64Image = qrCodeBase64Image;
	}

	public enum Provider {
		Bradesco, BancoDoBrasil, Simulado
	}

	public enum Type {
		CreditCard, DebitCard, ElectronicTransfer, Boleto, QRCode
	}

	public enum Currency {
		BRL, USD, MXN, COP, CLP, ARS, PEN, EUR, PYN, UYU, VEB, VEF, GBP
	}

    public ExternalAuthentication getExternalAuthentication() {
        return externalAuthentication;
    }

    public Payment setExternalAuthentication(ExternalAuthentication externalAuthentication) {
        this.externalAuthentication = externalAuthentication;
		return this;
    }

	public InitiatedTransactionIndicator getInitiatedTransactionIndicator() {
		return this.initiatedTransactionIndicator;
	}

	public Payment setInitiatedTransactionIndicator(InitiatedTransactionIndicator initiatedTransactionIndicator) {
		this.initiatedTransactionIndicator = initiatedTransactionIndicator;
		return this;
	}

	/**
	 * Categoria do indicador de início da transação. Válido apenas para bandeira Mastercard.
	 *
	 * Valores possíveis:
	 * - “C1”: transação inciada pelo portador do cartão;
	 * - “M1”: transação recorrente ou parcelada iniciada pela loja;
	 * - “M2”: transação iniciada pela loja.
	 */
	public enum Category {
		C1,
		M1,
		M2
	}

	/**
	 * Subcategoria do indicador. Válido apenas para bandeira Mastercard.
	 * Valores possíveis:
	 *
	 * Se InitiatedTransactionIndicator.Category = “C1” ou “M1”
	 * - CredentialsOnFile
	 * - StandingOrder
	 * - Subscription
	 * - Installment
	 *
	 * Se InitiatedTransactionIndicator.Category = “M2”
	 * - PartialShipment
	 * - RelatedOrDelayedCharge
	 * - NoShow
	 * - Resubmission
	 */
	public enum Subcategory {
		CredentialsOnFile,
		StandingOrder,
		Subscription,
		Installment,
		PartialShipment,
		RelatedOrDelayedCharge,
		NoShow,
		Resubmission
	}
}
