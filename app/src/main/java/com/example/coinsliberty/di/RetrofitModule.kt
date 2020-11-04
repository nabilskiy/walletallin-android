package com.example.coinsliberty.di

import com.example.coinsliberty.api.FileUploadService
import com.example.coinsliberty.api.LoginApi
import com.example.coinsliberty.api.UserApi
import com.example.coinsliberty.network.interceptors.ParamsInterceptor
import com.example.coinsliberty.network.interceptors.ResponseInterceptor
import com.example.coinsliberty.network.retrofit.RetrofitFactory
import com.example.coinsliberty.network.retrofit.RetrofitFactoryImpl
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
    single { get<Retrofit>().create(UserApi::class.java) }
    single { get<Retrofit>().create(FileUploadService::class.java) }
}
