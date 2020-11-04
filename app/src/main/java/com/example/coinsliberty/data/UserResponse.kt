package com.example.coinsliberty.data

import com.google.gson.annotations.SerializedName



data class UserResponse(
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("opt_enabled")
    val optEnabled: Boolean? = null,
    @SerializedName("avatar")
    val avatar: Long? = null,
    @SerializedName("login")
    val login: String? = null
)