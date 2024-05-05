import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * KeyStore : 안드로이드 시스템에 키를 안전하게 저장하는데 사용하는 클래스다.
 * KeyStore 시스템을 사용하면 안전하게 키를 관리할 수 있음
 * 1. 외부에서 키를 추출할 수 없음
 * 2. 키 사용에 대한 승인을 적용할 수 있음 (생체 인식 등)
 **/

/**
 * IV의 필요성 : IV(Initialization Vector)는 암호화 알고리즘에서 사용하는 초기화 벡터이다.
 * 암호화 블럭 단위로 암호화가 진행되는데, 이전에 암호화된 블럭과 현재 블럭의 평문을 XOR하여 암호화를 진행해 나감 (암호화 블럭 단위는 알고리즘별로 다름)
 * 하지만 첫 번째 블럭은 이전 블럭이 없으므로, IV를 사용하여 첫 번째 블럭을 암호화함
 * 암호화/복호화할 때 같은 IV를 사용해야 하므로, Cipher의 IV를 함께 저장해두고 복호화할 때 사용함
 **/

class CryptoManager {
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private val encryptCipher
        get() = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getKey()) // 예전에는 IV를 생성하여 사용했지만, 지금은 자동으로 생성되므로 IV를 사용하지 않아도 됨
        }

    private fun getDecryptCipherForIv(iv: ByteArray): Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv)) // IV를 사용하여 복호화
        }
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry("secret", null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    "secret",
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    fun encrypt(bytes: ByteArray, outputStream: OutputStream): ByteArray {
        val encryptedBytes = encryptCipher.doFinal(bytes)
        outputStream.use {
            it.write(encryptCipher.iv.size)
            it.write(encryptCipher.iv)
            it.write(encryptedBytes.size)
            it.write(encryptedBytes)
        }
        return encryptedBytes
    }

    fun decrypt(inputStream: InputStream): ByteArray {
        return inputStream.use {
            val ivSize = it.read()
            val iv = ByteArray(ivSize)
            it.read(iv)

            val encryptedBytesSize = it.read()
            val encryptedBytes = ByteArray(encryptedBytesSize)
            it.read(encryptedBytes)

            getDecryptCipherForIv(iv).doFinal(encryptedBytes)
        }
    }

    companion object {
        // AES256 알고리즘은 내부적으로 16바이트 암호화블럭 사이즈
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES

        // Cipher Block Chaining -> 암호화 블럭을 체이닝하는 방식
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC

        // 암호화 블럭이 16바이트가 되지 않을 때, 패딩을 추가하여 16바이트로 맞춤 / AES는 16바이트 블럭 사이즈를 가지므로 PCKS7을 사용함, PCKS5는 8바이트 블럭 사이즈를 가지는 DES에 사용
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7

        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}
