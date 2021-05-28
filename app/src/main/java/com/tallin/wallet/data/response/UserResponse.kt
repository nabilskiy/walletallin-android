package com.tallin.wallet.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserResponse(
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("otp_enabled")
    val optEnabled: Int? = null,
    @SerializedName("avatar")
    val avatar: Long? = null,
    @SerializedName("login")
    val login: String? = null
): Parcelable