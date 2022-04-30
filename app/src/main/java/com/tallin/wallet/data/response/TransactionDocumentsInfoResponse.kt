package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class TransactionDocumentsInfoResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null,
    @SerializedName("data")
    val data: List<DataTransactionDocumentsInfoResponse>? = null
)
data class DataTransactionDocumentsInfoResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("document_id")
    val documentId: Int? = null,
    @SerializedName("program_id")
    val programId: Int? = null,
    @SerializedName("kyc_info")
    val kycInfo: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("kycDocument")
    val kycDocument: DataTransactionDocumentsInfoResponseKycDocument? = null,
    //docWallet
    @SerializedName("kyc_status_text")
    val kycStatusText: String? = null,
    @SerializedName("kyc_status")
    val kycStatus: Int? = null,
    @SerializedName("assign_id")
    val assignId: Int? = null,
    @SerializedName("status_rendered")
    val statusRendered: String? = null,
    @SerializedName("tx_status")
    val txStatus: String? = null,
    @SerializedName("tx_compliance")
    val txCompliance: Int? = null,
    @SerializedName("images")
    val images: List<ImageTransactionDocumentsInfoResponseKycDocument>? = null
)

data class DataTransactionDocumentsInfoResponseKycDocument(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("doc_type_id")
    val docTypeId: Int? = null,
    @SerializedName("doc_value")
    val docValue: String? = null,
    @SerializedName("document_order")
    val documentOrder: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("doc_expire_in_days")
    val docExpireInDays: Int? = null,
    @SerializedName("updated_at")
    val updatedAt: Long? = null,
)

data class ImageTransactionDocumentsInfoResponseKycDocument(
    @SerializedName("name")
    val kycStatusText: String? = null,
    @SerializedName("created_at")
    val kycStatus: Long? = null,
    @SerializedName("updated_at")
    val assignId: Long? = null,
    @SerializedName("base64Image")
    val statusRendered: String? = null,
)