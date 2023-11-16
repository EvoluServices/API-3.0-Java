package cieloecommerce.sdk.ecommerce;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Identifica se a transação foi iniciada pelo titular do cartão ou pela loja. Válido apenas para
 * bandeira Mastercard.
 *
 * Category: Categoria do indicador de início da transação.
 * Valores possíveis:
 * - “C1”: transação inciada pelo portador do cartão;
 * - “M1”: transação recorrente ou parcelada iniciada pela loja;
 * - “M2”: transação iniciada pela loja.
 *
 * Subcategory: Subcategoria do indicador.
 * Valores possíveis:
 *
 * Se Category = “C1” ou “M1”
 * - CredentialsOnFile
 * - StandingOrder
 * - Subscription
 * - Installment
 *
 * Se Category = “M2”
 * - PartialShipment
 * - RelatedOrDelayedCharge
 * - NoShow
 * - Resubmission
 */
public class InitiatedTransactionIndicator {
  @SerializedName("Category")
  private String category;

  @SerializedName("Subcategory")
  private String subcategory;

  public String getCategory() {
    return this.category;
  }

  public InitiatedTransactionIndicator setCategory(String category) {
    this.category = category;
    return this;
  }

  public String getSubcategory() {
    return this.subcategory;
  }

  public InitiatedTransactionIndicator setSubcategory(String subcategory) {
    this.subcategory = subcategory;
    return this;
  }
}
