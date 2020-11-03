package com.example.coinsliberty.network.retrofit

import com.google.gson.Gson
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.example.coinsliberty.network.interceptors.ParamsInterceptor
import com.example.coinsliberty.network.interceptors.ResponseInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactoryImpl(private val paramsInterceptor: ParamsInterceptor,
                          private val responseInterceptor: ResponseInterceptor,
                          private val chuckInterceptor: ChuckInterceptor) : RetrofitFactory {

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
            .baseUrl("http://api.test-wallet.coinsliberty.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())

        return builder.build()
    }

    companion object {
        private const val TIMEOUT_SECS: Long = 60
    }

}