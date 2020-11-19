package com.coinsliberty.wallet.utils.biometric.fingerprint

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

object FingerprintHelper {

    private fun checkSensorState(context: Context): SensorState {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return SensorState.NOT_SUPPORTED

        val permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT)

        if (permission != PackageManager.PERMISSION_GRANTED) return SensorState.NOT_SUPPORTED

        val fingerprintManager = FingerprintManagerCompat.from(context)

        if (fingerprintManager.isHardwareDetected) {
            val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

            if (keyguardManager.isKeyguardSecure.not()) return SensorState.NOT_BLOCKED

            return if (fingerprintManager.hasEnrolledFingerprints().not()) SensorState.NO_FINGERPRINTS else SensorState.READY
        }

        return SensorState.NOT_SUPPORTED
    }

    fun sensorIsReady(context: Context): Boolean {
//        if (BuildConfig.ENABLE_FINGERPRINT.not()) return false

        val state =
            checkSensorState(
                context
            )

        return state == SensorState.READY
    }

    enum class SensorState {
        NOT_SUPPORTED,
        NOT_BLOCKED, // если устройство не защищено пином, рисунком или паролем
        NO_FINGERPRINTS, // если на устройстве нет отпечатков
        READY
    }
}