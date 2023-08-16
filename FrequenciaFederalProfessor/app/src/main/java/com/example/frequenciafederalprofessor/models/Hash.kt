package com.example.frequenciafederalprofessor.models

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordHasher {
    private const val SALT_LENGTH = 16
    private const val ITERATIONS = 10000
    private const val ALGORITHM = "SHA-256"

    fun hashPassword(password: String): String {
        val random = SecureRandom()
        val salt = ByteArray(SALT_LENGTH)
        random.nextBytes(salt)

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, 256)
        val hash = factory.generateSecret(spec).encoded

        return salt.toHexString() + hash.toHexString()
    }

    private fun ByteArray.toHexString(): String {
        val result = StringBuilder()
        for (b in this) {
            result.append(String.format("%02x", b))
        }
        return result.toString()
    }
}
