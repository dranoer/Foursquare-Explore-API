package com.nightmareinc.foursquare.model.models

import com.google.gson.annotations.SerializedName

data class Item (

    @field:SerializedName("venue")
    val venue: Venue

)