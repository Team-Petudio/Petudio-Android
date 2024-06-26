package com.composition.damoa

import android.app.Application
import com.composition.damoa.presentation.service.fcm.PetudioNotificationChannel
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@HiltAndroidApp
class PetudioApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initNotificationChannels()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }

    private fun initNotificationChannels() {
        PetudioNotificationChannel.createChannels(this)
    }

    companion object {
        val scope = CoroutineScope(Dispatchers.IO)
    }
}
