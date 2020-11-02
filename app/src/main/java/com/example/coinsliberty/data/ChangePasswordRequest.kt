package com.example.coinsliberty.data

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("old_password")
    val oldPassword: String? = null
)