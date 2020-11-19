package com.coinsliberty.wallet.utils.manager

import android.content.Context
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.utils.biometric.BiometricHelper

class BiometricManager(private val securePreferenceHelper: SharedPreferencesProvider, val context: Context) {

    fun checkEnableBiometric() = when {
            BiometricHelper.checkSensor(context).not() || securePreferenceHelper.canUseBiometric().not() -> TypeBiometrics.NONE
            BiometricHelper.checkFingerprintAvailable(context) -> TypeBiometrics.TOUCH_ID
            else -> TypeBiometrics.FACE_ID
        }

    fun getTypeOfBiometric() = when {
        BiometricHelper.checkSensor(context).not() -> TypeBiometrics.NONE
        BiometricHelper.checkFingerprintAvailable(context) -> TypeBiometrics.TOUCH_ID
        else -> TypeBiometrics.FACE_ID
    }

    fun needShowDialog() = (BiometricHelper.checkSensor(context))

}

sealed class TypeBiometrics(val id: Int) {
    object NONE : TypeBiometrics(0)
    object TOUCH_ID : TypeBiometrics(1)
    object FACE_ID : TypeBiometrics(2)
}