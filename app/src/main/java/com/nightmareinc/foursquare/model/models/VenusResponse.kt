package com.nightmareinc.foursquare.model.models

import com.google.gson.annotations.SerializedName

data class VenusResponse(

    @field:SerializedName("groups")
    val groups: List<Group>

)