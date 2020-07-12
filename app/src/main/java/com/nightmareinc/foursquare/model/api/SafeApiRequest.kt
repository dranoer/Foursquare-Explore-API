package com.nightmareinc.foursquare.model.api

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

import com.nightmareinc.foursquare.util.ApiException

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = call.invoke()
        val message = StringBuilder()

        if (response?.isSuccessful) {
            Log.d("nzn","safeapireqsucc+${response.body()} ")

            return response?.body()!!

        }
        else {
            val error = response?.errorBody()?.string()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))

                } catch (e: JSONException) {
                    message.append("\n")
                }

            }
            message.append("\nError code ${response.code()}")
            Log.d("nzn","safeapireqfail 00000+${response.code()} ")
            throw ApiException(message.toString())
        }
    }
}