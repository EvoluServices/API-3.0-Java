package cieloecommerce.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;

public class SubEstablishment {

  @SerializedName("EstablishmentCode")
  private String establishmentCode;
  @SerializedName("CompanyName")
  private String companyName;
  @SerializedName("Identity")
  private String identity;
  @SerializedName("Mcc")
  private String mcc;
  @SerializedName("Address")
  private String address;
  @SerializedName("City")
  private String city;
  @SerializedName("State")
  private String state;
  @SerializedName("PostalCode")
  private String postalCode;
  @SerializedName("CountryCode")
  private String countryCode;
  @SerializedName("PhoneNumber")
  private String phoneNumber;

  public String getEstablishmentCode() {
    return this.establishmentCode;
  }

  public SubEstablishment setEstablishmentCode(String establishmentCode) {
    this.establishmentCode = establishmentCode;
    return this;
  }

  public SubEstablishment setCompanyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getIdentity() {
    return this.identity;
  }

  public SubEstablishment setIdentity(String identity) {
    this.identity = identity;
    return this;
  }

  public String getMcc() {
    return this.mcc;
  }

  public SubEstablishment setMcc(String mcc) {
    this.mcc = mcc;
    return this;
  }

  public String getAddress() {
    return this.address;
  }

  public SubEstablishment setAddress(String address) {
    this.address = address;
    return this;
  }

  public String getCity() {
    return this.city;
  }

  public SubEstablishment setCity(String city) {
    this.city = city;
    return this;
  }

  public String getState() {
    return this.state;
  }

  public SubEstablishment setState(String state) {
    this.state = state;
    return this;
  }

  public String getPostalCode() {
    return this.postalCode;
  }

  public SubEstablishment setPostalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  public String getCountryCode() {
    return this.countryCode;
  }

  public SubEstablishment setCountryCode(String countryCode) {
    this.countryCode = countryCode;
    return this;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public SubEstablishment setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }
}
