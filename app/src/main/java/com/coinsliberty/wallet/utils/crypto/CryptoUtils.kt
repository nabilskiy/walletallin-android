package com.coinsliberty.wallet.utils.crypto

import org.bouncycastle.util.encoders.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

private const val KEY_TOKEN = "OMYONDKZDPQIHQZNYZGMCJPVKWSOILBW"
private const val VECTOR_TOKEN = "LINKWCVTMOZUXJWN"

fun encryptData(token: String?): String? {

    token ?: return null

    val iv = IvParameterSpec(VECTOR_TOKEN.toByteArray())
    val keySpec = SecretKeySpec(KEY_TOKEN.toByteArray(), "AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)

    val encrypted = cipher.doFinal(token.toByteArray())
    return String(Base64.encode(encrypted))
}

fun decryptData(token: String?): String? {

    token ?: return null

    val iv = IvParameterSpec(VECTOR_TOKEN.toByteArray())
    val keySpec = SecretKeySpec(KEY_TOKEN.toByteArray(), "AES")

    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
    val original = cipher.doFinal(Base64.decode(token))

    return String(original)
}