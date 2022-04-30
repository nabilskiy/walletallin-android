package com.tallin.wallet.data.response

import com.tallin.wallet.utils.currency.Currency
import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("transactions")
    val item: List<TransactionItem>? = null,
    /*@SerializedName("total")
    val total: Int? = null,*/
    @SerializedName("error")
    val error: ErrorResponse? = null
)

data class TransactionItem(
    @SerializedName("address")//+
    val address: String? = null,
    @SerializedName("amount")//+
    val amount: String? = null,
    @SerializedName("amountUsd")//-
    var amountUsd: String? = null,
    @SerializedName("bip125-replaceable")//+
    val replaceable: String? = null,
    @SerializedName("category")//+
    val category: String? = null,
    @SerializedName("confirmations")//+
    val confirmations: Int? = null,
    @SerializedName("time")//+
    val time: Long? = null,
    @SerializedName("timereceived")//+
    val timereceived: Long? = null,
    @SerializedName("txid")//+
    val txid: String? = null,
    @SerializedName("typeItem")//-
    var typeItem: Int? = 1,
    @SerializedName("currency")//-
    var currency: Currency = Currency.USD,
    //---------------------------------------//
    @SerializedName("crypto")//+
    val crypto: Boolean? = null,
    @SerializedName("num")//+
    val num: Int? = null,
    @SerializedName("asset")//+
    val asset: String? = null,
    @SerializedName("finalAmount")//+
    val finalAmount: String? = null,
    @SerializedName("fiat")//+
    val fiat: String? = null,
    @SerializedName("status")//+
    val status: String? = null,
    @SerializedName("assign_id")//+
    val assign_id: Int? = null,
    @SerializedName("compliance")//+
    val compliance: Int? = null,
   /* @SerializedName("images")//+
    val images: String? = null,*/
)