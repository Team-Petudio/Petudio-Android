package com.composition.damoa.data.dataSource.local.serializer

import CryptoManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
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

    companion object {
        private const val DATA_STORE_FILE_NAME = "token_data_store.json"
        private val Context.tokenDataStore: DataStore<Token> by dataStore(
            fileName = DATA_STORE_FILE_NAME,
            serializer = TokenSerializer(CryptoManager())
        )

        fun getDataStore(context: Context): DataStore<Token> {
            return context.tokenDataStore
        }
    }
}
