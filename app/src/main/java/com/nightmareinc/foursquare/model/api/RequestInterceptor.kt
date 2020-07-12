package com.nightmareinc.foursquare.model.api

import okhttp3.Interceptor
import okhttp3.Response

import com.nightmareinc.foursquare.util.Constant

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val url =  chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("v" , Constant.DEVELOPMENT_DATE)
            .addQueryParameter("client_id" , Constant.CLIENT_ID)
            .addQueryParameter("client_secret" , Constant.CLIENT_SECRET)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

}