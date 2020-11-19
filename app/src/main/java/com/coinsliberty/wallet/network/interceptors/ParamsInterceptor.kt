package com.coinsliberty.wallet.network.interceptors

import com.coinsliberty.wallet.model.SharedPreferencesProvider
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class ParamsInterceptor(val securePreferenceHelper: SharedPreferencesProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
                .build()

        val request = chain.request().newBuilder().apply {
            url(url)
            header("Content-Type", "application/json")
//            header("Content-Length", "68")
            header("Date", Date().toString())
            header("User-Agent", "coinsliberty-app")
            header("Connection", "keep-alive")
            header("X-Powered-By", "Express")
            header("Vary", "Origin")
            header("Access-Control-Allow-Credentials", "true")
            header("ETag", "W/\"44-Q9BmkzXWFxPhNN+eNc7Yvklw7bM\"")
            if(securePreferenceHelper.getToken()?.isNotEmpty() == true) {
                addHeader("slc-auth", securePreferenceHelper.getToken() ?: "")
            }
        }.build()

        return chain.proceed(request)
    }

}