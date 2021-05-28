package com.tallin.wallet.utils.biometric

import android.content.Context
import android.content.pm.PackageManager
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object BiometricHelper {

    fun createBiometricPrompt(activity: FragmentActivity, callback: BiometricPrompt.AuthenticationCallback) =
        BiometricPrompt(activity, ContextCompat.getMainExecutor(activity), callback)

    fun createBiometricPrompt(fragment: Fragment, callback: BiometricPrompt.AuthenticationCallback) =
        BiometricPrompt(fragment, ContextCompat.getMainExecutor(fragment.context), callback)

    fun checkSensor(context: Context): Boolean {
        val manager = BiometricManager.from(context)

        return manager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun checkFingerprintAvailable(context: Context) =
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)

    fun buildDefaultBiometricDialog(title: String, subTitle: String?, cancelBtnTitle: String) = BiometricPrompt.PromptInfo.Builder()
        .setTitle(title)
        .setSubtitle(subTitle)
        .setNegativeButtonText(cancelBtnTitle)
        .build()
}