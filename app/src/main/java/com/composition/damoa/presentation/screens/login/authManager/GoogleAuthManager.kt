package com.composition.damoa.presentation.screens.login.authManager

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.composition.damoa.R
import com.composition.damoa.data.model.User
import com.composition.damoa.data.network.retrofit.callAdapter.Success
import com.composition.damoa.data.repository.interfaces.GoogleRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GoogleAuthManager(
    private val context: Context,
    private val googleRepository: GoogleRepository,
) : AuthManager() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val clientId = context.getString(R.string.google_client_id)
    private val clientSecret = context.getString(R.string.google_client_secret)
    private val resultLauncher =
        (context as ComponentActivity).registerForActivityResult(StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

    override fun login(
        socialType: User.SocialType,
        onPreLogin: () -> Unit,
        onSuccess: (accessToken: String, fcmToken: String) -> Unit,
        onFailure: (error: Throwable) -> Unit,
    ) {
        super.login(socialType, onPreLogin, onSuccess, onFailure)

        with(getGoogleClient(context)) {
            signOut().addOnCompleteListener {
                resultLauncher.launch(signInIntent)
            }
        }
    }

    private fun getGoogleClient(context: Context): GoogleSignInClient {
        val googleSignInClientOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestServerAuthCode(clientId)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, googleSignInClientOptions)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val authCode = requireNotNull(account.serverAuthCode)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                handleAuthResult(task, authCode)
            }
        } catch (e: Exception) {
            fail(e)
            when (e) {
                is ApiException -> Log.d("buna", "[ERROR] 구글 로그인 실패 코드 : ${e.statusCode}")
                is NullPointerException -> Log.d("buna", "[ERROR] 구글 로그인 토큰이 null 입니다.")
            }
        }
    }

    private fun handleAuthResult(
        task: Task<AuthResult>,
        authCode: String,
    ) {
        if (!task.isSuccessful) {
            fail(task.exception ?: Exception("[ERROR] 구글 로그인 실패"))
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            when (val token = googleRepository.getAccessToken(authCode, clientId, clientSecret)) {
                is Success -> FirebaseMessaging.getInstance().token.addOnSuccessListener { fcmToken ->
                    success(token.data, fcmToken)
                }

                else -> fail(Exception("[ERROR] AuthCode로 AccessToken을 받아오는데 실패했습니다."))
            }
        }
    }
}