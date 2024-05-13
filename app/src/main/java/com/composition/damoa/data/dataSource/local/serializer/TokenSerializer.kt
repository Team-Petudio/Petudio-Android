package com.composition.damoa.data.dataSource.local.serializer

import CryptoManager
import androidx.datastore.core.Serializer
import com.composition.damoa.data.model.User.Token
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class TokenSerializer(
    private val cryptoManager: CryptoManager,
) : Serializer<Token> {
    override val defaultValue: Token
        get() = Token()

    override suspend fun readFrom(input: InputStream): Token {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = Token.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: Token, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = Token.serializer(),
                value = t,
            ).encodeToByteArray(),
            outputStream = output,
        )
    }
}
