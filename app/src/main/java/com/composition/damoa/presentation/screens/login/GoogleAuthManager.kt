package com.composition.damoa.presentation.screens.login

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoogleAuthManager(
    activity: ComponentActivity,
    private val googleRepository: GoogleRepository,
) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val clientId = activity.getString(R.string.google_client_id)
    private val clientSecret = activity.getString(R.string.google_client_secret)
    private var successCallback: (accessToken: String) -> Unit = {}
    private val resultLauncher = activity.registerForActivityResult(StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task)
    }
    private lateinit var context: Context

    fun login(
        context: Context,
        onSuccess: (accessToken: String) -> Unit,
    ) {
        this.context = context

        successCallback = onSuccess
        with(getGoogleClient(context)) {
            signOut().addOnCompleteListener {
                resultLauncher.launch(signInIntent)
            }
        }
    }

    private fun getGoogleClient(context: Context): GoogleSignInClient {
        val googleSignInClient = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestServerAuthCode(clientId)
            .build()

        return GoogleSignIn.getClient(context, googleSignInClient)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val authCode = requireNotNull(account.serverAuthCode)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val token = googleRepository.getAccessToken(authCode, clientId, clientSecret)
                        if (token is Success) {
                            val a = token.data
                            Log.d("buna", "token : $a")
                            successCallback(a)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            when (e) {
                is ApiException -> Log.d("buna", "[ERROR] 구글 로그인 실패 코드 : ${e.statusCode}")
                is NullPointerException -> Log.d("buna", "[ERROR] 구글 로그인 토큰이 null 입니다.")
            }
        }
    }
}