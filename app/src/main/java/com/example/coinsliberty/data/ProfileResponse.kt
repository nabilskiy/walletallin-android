package com.example.coinsliberty.data

import com.google.gson.annotations.SerializedName


data class ProfileResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("user")
    val user: UserResponse? = null

)