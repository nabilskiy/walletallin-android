package com.example.coinsliberty.data

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("password")
    val email: String? = null,
    @SerializedName("old_password")
    val password: String? = null
)