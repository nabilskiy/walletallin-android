package com.coinsliberty.wallet.utils.biometric.fingerprint

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal


@RequiresApi(Build.VERSION_CODES.M)
class FingerprintHandler(private val fingerprintCallback: FingerprintCallback) : FingerprintManagerCompat.AuthenticationCallback() {

    private lateinit var cancellationSignal: CancellationSignal

    fun startAuth(manager: FingerprintManagerCompat) {
        cancellationSignal = CancellationSignal()
        manager.authenticate(null, 0, cancellationSignal, this, null)
    }

    fun isCanceled() = cancellationSignal.isCanceled

    fun cancel() {
        cancellationSignal.cancel()
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
        fingerprintCallback.onAuthenticationError(errMsgId, errString)
    }

    override fun onAuthenticationFailed() {
        fingerprintCallback.onAuthenticationFailed()
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
        fingerprintCallback.onAuthenticationHelp(helpMsgId, helpString)
    }

    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult) {
        fingerprintCallback.onAuthenticationSucceeded(result)
    }

    interface FingerprintCallback {
        fun onAuthenticationError(errMsgId: Int, errString: CharSequence)
        fun onAuthenticationFailed()
        fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult)
        fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence)
    }
}