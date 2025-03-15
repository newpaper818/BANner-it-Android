package com.fitfit.core.data.local_db

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

const val BYTES_TO_STRING_SEPARATOR = "|"
const val IV_TO_STRING_SEPARATOR = ":iv:"

private const val PROVIDER = "AndroidKeyStore"


object SecurityUtil {
    private val cipher by lazy {
        Cipher.getInstance("AES/GCM/NoPadding")
    }
    private val charset by lazy {
        charset("UTF-8")
    }
    private val keyStore by lazy {
        KeyStore.getInstance(PROVIDER).apply {
            load(null)
        }
    }
    private val keyGenerator by lazy {
        KeyGenerator.getInstance(KEY_ALGORITHM_AES, PROVIDER)
    }

    fun encryptData(keyAlias: String, text: String): Pair<ByteArray, ByteArray> {
        val secretKey = generateSecretKey(keyAlias)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedData = cipher.doFinal(text.toByteArray(charset))
        val iv = cipher.iv
        return Pair(iv, encryptedData)
    }

    fun decryptData(keyAlias: String, iv: ByteArray, encryptedData: ByteArray): String? {
        val secretKey = getSecretKey(keyAlias) ?: return null
        val gcmParameterSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec)
        return cipher.doFinal(encryptedData).toString(charset)
    }

    private fun generateSecretKey(keyAlias: String): SecretKey? {

        val keyEntry = keyStore.getEntry(keyAlias, null)
        if (keyEntry== null) {
            return keyGenerator.apply {
                init(
                    KeyGenParameterSpec
                        .Builder(keyAlias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE_GCM)
                        .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                        .build()
                )
            }.generateKey()
        }
        else {
            return getSecretKey(keyAlias)
        }
    }

    private fun getSecretKey(
        keyAlias: String
    ): SecretKey?{
        return (keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry)?.secretKey
    }
}

object JwtSecure{
    /** combine iv and secureByteArray to one string*/
    fun encodeIvAndData(
        iv: ByteArray,
        secureByteArray: ByteArray
    ): String {
        return iv.joinToString(BYTES_TO_STRING_SEPARATOR) + IV_TO_STRING_SEPARATOR + secureByteArray.joinToString(BYTES_TO_STRING_SEPARATOR)
    }

    /** split jwtSecured string*/
    fun decodeIvAndData(
        jwtSecured: String
    ): Pair<ByteArray, ByteArray>{
        val (ivString, encryptedString) = jwtSecured.split(IV_TO_STRING_SEPARATOR, limit = 2)
        val iv = ivString.split(BYTES_TO_STRING_SEPARATOR).map { it.toByte() }.toByteArray()
        val encryptedData = encryptedString.split(BYTES_TO_STRING_SEPARATOR).map { it.toByte() }.toByteArray()
        return Pair(iv, encryptedData)
    }
}
