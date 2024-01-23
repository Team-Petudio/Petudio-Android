package com.composition.damoa.presentation.screens.login.authManager

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.composition.damoa.R
import com.composition.damoa.data.common.retrofit.callAdapter.Success
import com.composition.damoa.data.repository.GoogleRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoogleAuthManager(
    private val activity: ComponentActivity,
    private val googleRepository: GoogleRepository,
) : AuthManager() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val clientId = activity.getString(R.string.google_client_id)
    private val clientSecret = activity.getString(R.string.google_client_secret)
    private val resultLauncher = activity.registerForActivityResult(StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task)
    }

    override fun login(
        onSuccess: (accessToken: String) -> Unit,
        onFailure: (error: Throwable) -> Unit,
    ) {
        super.login(onSuccess, onFailure)

        with(getGoogleClient(activity)) {
            signOut().addOnCompleteListener {
                resultLauncher.launch(signInIntent)
            }
        }
    }

    private fun getGoogleClient(context: Context): GoogleSignInClient {
        val googleSignInClientOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestServerAuthCode(clientId)
            .build()

        return GoogleSignIn.getClient(context, googleSignInClientOptions)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val authCode = requireNotNull(account.serverAuthCode)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { authResult ->
                handleAuthResult(authResult, authCode)
            }
        } catch (e: Exception) {
            when (e) {
                is ApiException -> Log.d("buna", "[ERROR] 구글 로그인 실패 코드 : ${e.statusCode}")
                is NullPointerException -> Log.d("buna", "[ERROR] 구글 로그인 토큰이 null 입니다.")
            }
            failureCallback(e)
        }
    }

    private fun handleAuthResult(
        task: Task<AuthResult>,
        authCode: String,
    ) {
        if (!task.isSuccessful) {
            failureCallback(task.exception ?: Exception("[ERROR] 구글 로그인 실패"))
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            when (val token = googleRepository.getAccessToken(authCode, clientId, clientSecret)) {
                is Success -> auth.signInWithCustomToken(token.data)
                else -> failureCallback(Exception("[ERROR] AuthCode로 AccessToken을 받아오는데 실패했습니다."))
            }
        }
    }
}