package com.tallin.wallet.network.retrofit

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tallin.wallet.BuildConfig
import com.google.gson.Gson

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.tallin.wallet.network.interceptors.ParamsInterceptor
import com.tallin.wallet.network.interceptors.ResponseInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactoryImpl(private val paramsInterceptor: ParamsInterceptor,
                          private val responseInterceptor: ResponseInterceptor,
                          private val chuckInterceptor: ChuckerInterceptor) : RetrofitFactory {

    override fun createRetrofit(gson: Gson): Retrofit {
        val okHttpBuilder =
            OkHttpClient().newBuilder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(loggingInterceptor)
        okHttpBuilder.addInterceptor(chuckInterceptor)
        okHttpBuilder.addInterceptor(paramsInterceptor)
            .addInterceptor(responseInterceptor)
            .connectTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)

        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())

        return builder.build()
    }

    companion object {
        private const val TIMEOUT_SECS: Long = 60
    }

}