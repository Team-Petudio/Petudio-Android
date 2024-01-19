package com.composition.damoa

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class PetudioApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}
