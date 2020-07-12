package com.nightmareinc.foursquare.model.models

import com.google.gson.annotations.SerializedName

data class FoursquareResponse (

    @field:SerializedName("response")
    val response: VenusResponse

)