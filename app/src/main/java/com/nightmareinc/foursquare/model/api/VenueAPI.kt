package com.nightmareinc.foursquare.model.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

import com.nightmareinc.foursquare.model.models.FoursquareResponse

import com.nightmareinc.foursquare.util.Constant

interface VenueAPI {

    companion object {
        operator fun invoke(
            interceptor: RequestInterceptor
        ): VenueAPI {

            val gson = GsonBuilder().setLenient().create()

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(VenueAPI::class.java)
        }
    }

    @GET("venues/explore")
    suspend fun getAllVenues(
        @Query("ll") latlng: String?
    ) : Response<FoursquareResponse>

}