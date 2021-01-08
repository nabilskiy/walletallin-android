package com.coinsliberty.wallet.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EditProfileRequest(
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("otp_enabled")
    val otpEnabled: Boolean? = null,
    @SerializedName("otp")
    var otp: String? = null,
    @SerializedName("avatar")
    val avatar: Long? = null
): Parcelable