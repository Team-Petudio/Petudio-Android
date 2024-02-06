-dontwarn org.xmlpull.v1.**
-dontwarn org.kxml2.io.**
-dontwarn android.content.res.**

-keep class org.xmlpull.** { *; }
-keepclassmembers class org.xmlpull.** { *; }

# ApiResponse 클래스의 타입 매개변수를 유지하기 위해 추가.
# 안하면 CallAdapter에서 retrofit2.Call<ApiResponse>를 반환하는 CallAdapter 만들 수 없음.
-keepnames, allowobfuscation class com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse

# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>
-keep interface com.kakao.sdk.**.*Api
-keep class com.kakao.sdk.**.model.* { <fields>; }
-keep class * extends com.google.gson.TypeAdapter

-keep public class com.google.firebase.analytics.FirebaseAnalytics {
    public *;
}

-keep public class com.google.android.gms.measurement.AppMeasurement {
    public *;
}

-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.*
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE