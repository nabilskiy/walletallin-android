package com.tallin.wallet.data

import com.google.gson.annotations.SerializedName

data class SignUpRequest(

    @SerializedName("wallet_type_id")
    val wallet_type_id: String? = null,
    @SerializedName("first_name")
    val first_name: String? = null,
    @SerializedName("last_name")
    val last_name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null,

    // Business
    @SerializedName("company_email")
    val company_email: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("company_number")
    val company_number: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("website")
    val website: String? = null,
    @SerializedName("first_name_director")
    val first_name_director: String? = null,
    @SerializedName("last_name_director")
    val last_name_director: String? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("street")
    val street: String? = null,
    @SerializedName("postal_code")
    val postal_code: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("description")
    val description: String? = null,
)