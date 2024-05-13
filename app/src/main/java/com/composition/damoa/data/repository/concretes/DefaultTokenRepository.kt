package com.composition.damoa.data.repository.concretes

import com.composition.damoa.data.dataSource.local.interfaces.TokenDataSource
import com.composition.damoa.data.model.User
import com.composition.damoa.data.network.dto.request.ReissueTokenRequest
import com.composition.damoa.data.network.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.network.retrofit.callAdapter.Success
import com.composition.damoa.data.network.retrofit.utils.TokenParser
import com.composition.damoa.data.network.service.TokenService
import com.composition.damoa.data.repository.interfaces.TokenRepository


class DefaultTokenRepository(
    private val service: TokenService,
    private val tokenDataSource: TokenDataSource,
) : TokenRepository {

    override suspend fun reissueToken(): ApiResponse<Unit> {
        return when (val reissueResult = service.reissueToken(ReissueTokenRequest(tokenDataSource.getToken()))) {
            is Success -> {
                TokenParser.parseHeaders(reissueResult.headers).also { tokenDataSource.saveToken(it) }
                Success(Unit)
            }

            else -> reissueResult
        }
    }

    override suspend fun saveToken(token: User.Token) {
        tokenDataSource.saveToken(token)
    }

    override suspend fun getToken(): User.Token {
        return tokenDataSource.getToken()
    }

    override suspend fun deleteToken() {
        tokenDataSource.deleteToken()
    }
}