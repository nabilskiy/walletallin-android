package com.tallin.wallet.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class UserResponse(
    @SerializedName("otp_enabled")//+
    val optEnabled: Int? = null,
    @SerializedName("login")//+
    val login: String? = null,
    @SerializedName("is_active")//+
    val isActive: Int? = null,
    @SerializedName("first_name")//+
    val firstName: String? = null,
    @SerializedName("last_name")//+
    val lastName: String? = null,
    @SerializedName("phone")//+
    val phone: String? = null,
    @SerializedName("role")//+
    val role: String? = null,
    @SerializedName("wallet_id")//+
    val walletId: Int? = null,
    @SerializedName("idp")//+
    val idp: String? = null,
    @SerializedName("api")//+
    val api: Boolean? = null,
    @SerializedName("avatar")//+
    val avatar: Long? = null,
  /*  @SerializedName("flowid_getid")
    val flowidGetid: String? = null,
    @SerializedName("flowName")
    val flowName: String? = null,
    @SerializedName("getIdUrl")
    val getIdUrl:String? = null,
    @SerializedName("apiKeyGetId")
    val apiKeyGetId: String? = null,*/

    @SerializedName("wallet_type_id")//+
    val wallet_type_id: Int? = null,

    @SerializedName("wallet")//+
    val wallet: UserResponseWallet? = null,
    @SerializedName("kycProgram")//+
    val kycProgram: UserResponseKycProgram? = null,
    @SerializedName("company")//+
    val company: UserResponseCompany? = null
)

@Parcelize
data class UserResponseWallet(
    @SerializedName("id")//+
    val id: Int? = null,
    @SerializedName("wallet_type")//+
    val walletType: String? = null,
    @SerializedName("kyc_program_id")//+
    val kycProgramId: Int? = null,
    @SerializedName("kyc_program_status")//+
    val kycProgramStatus: Int? = null,
    @SerializedName("createdAt")//+
    val createdAt: String? = null,
    @SerializedName("updatedAt")//+
    val updatedAt: String? = null
): Parcelable

@Parcelize
data class UserResponseKycProgram(
    @SerializedName("flowid_getid")
    val flowidGetid: String? = null,
    @SerializedName("flowName")
    val flowName: String? = null,
    @SerializedName("getIdUrl")//+
    val getIdUrl:String? = null,
    @SerializedName("apiKeyGetId")//+
    val apiKeyGetId: String? = null,
    @SerializedName("name")//+
    val name: String? = null,
    @SerializedName("description")//+
    val description: String? = null,
    @SerializedName("default")//+
    val default: Boolean? = null,
    @SerializedName("kycDocuments")//+
    val kycDocuments: ArrayList<KycDocuments>? = null
): Parcelable

@Parcelize
data class UserResponseCompany(
    @SerializedName("id")//+
    val id: Int? = null,
    @SerializedName("name")//+
    val name: String? = null,
    @SerializedName("company_number")//+
    val companyNumber: String? = null,
    @SerializedName("company_email")//+
    val companyEmail: String? = null,
    //val companyEmail: Int? = null,
    @SerializedName("phone")//+
    val phone: String? = null,
    //val phone: Long? = null,
    @SerializedName("website")//+
    val website: String? = null,
    @SerializedName("first_name_director")//+
    val firstNameDirector: String? = null,
    @SerializedName("last_name_director")//+
    val lastNameDirector: String? = null,
    @SerializedName("city")//+
    val city: String? = null,
    @SerializedName("street")//+
    val street: String? = null,
    //val street: Int? = null,
    @SerializedName("postal_code")//+
    val postalCode: String? = null,
    //val postalCode: Long? = null,
    @SerializedName("country")//+
    val country: String? = null,
    @SerializedName("description")//+
    val description: String? = null,
    @SerializedName("user_login")//+
    val userLogin: String? = null,
    @SerializedName("createdAt")//+
    val createdAt: String? = null,
    @SerializedName("updatedAt")//+
    val updatedAt: String? = null,
): Parcelable

@Parcelize
data class KycDocuments(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("doc_id")
    val docId: Int? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("doc_type_id")
    val docTypeId: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("flowName")
    val flowName: String? = null,
    @SerializedName("description")
    val description: String? = null
): Parcelable