package com.example.coinsliberty.network.interceptors

import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class ResponseInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        val body = response.body

        val string = body?.string()

        val bodyString = try { JsonParser().parse(string) } catch (e: Exception) { string }

        val contentType = body?.contentType()

        return response.newBuilder().body(ResponseBody.create(contentType, bodyString.toString())).build()

    }

}