package com.tallin.wallet.di

import com.tallin.wallet.api.*
import com.tallin.wallet.network.interceptors.ParamsInterceptor
import com.tallin.wallet.network.interceptors.ResponseInterceptor
import com.tallin.wallet.network.retrofit.RetrofitFactory
import com.tallin.wallet.network.retrofit.RetrofitFactoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val retrofitModule = module(override = true) {



    single { ChuckInterceptor(get()) }

    single { ParamsInterceptor(get()) }
    single { ResponseInterceptor() }
    single<Gson> { GsonBuilder().setLenient().create() }
    single<RetrofitFactory> { RetrofitFactoryImpl(get(), get(), get()) }
    single { get<RetrofitFactory>().createRetrofit(get()) }

    single { get<Retrofit>().create(LoginApi::class.java) }
    single { get<Retrofit>().create(WalletApi::class.java) }
    single { get<Retrofit>().create(UserApi::class.java) }
    single { get<Retrofit>().create(FileUploadService::class.java) }
    single { get<Retrofit>().create(BtcApi::class.java) }
    single { get<Retrofit>().create(ExchangeApi::class.java) }
 //   single { get<Retrofit>().create(KycApi::class.java) }

}
