package com.nightmareinc.foursquare.model.models

import com.google.gson.annotations.SerializedName

data class VenueLocation (

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("crossStreet")
    val crossStreet: String? = null,

    @field:SerializedName("lat")
    val lat: Double? = null,

    @field:SerializedName("lng")
    val lng: Double? = null,

    @field:SerializedName("distance")
    val distance: Int? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("country")
    val country: String? = null

)