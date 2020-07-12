package com.nightmareinc.foursquare.model.models

import com.google.gson.annotations.SerializedName

data class VenueList(

    @field:SerializedName("items")
    val venues: List<Venue>

)