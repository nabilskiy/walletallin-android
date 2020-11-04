package com.example.coinsliberty.data

import com.google.gson.annotations.SerializedName

data class EditProfileResponse(
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("opt_enabled")
    val optEnabled: Boolean? = null,
    @SerializedName("file")
    val photo: Any? = null,

)