package com.coinsliberty.wallet.data.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("files")
    val item: Map<String, Long>? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null
)

data class FilesItemUpload(
    @SerializedName("file")
    val file: Long? = null
)