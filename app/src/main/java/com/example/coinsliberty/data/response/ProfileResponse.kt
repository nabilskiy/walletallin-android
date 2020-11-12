package com.example.coinsliberty.data.response

import com.google.gson.annotations.SerializedName


data class ProfileResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("user")
    val user: UserResponse? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null

)