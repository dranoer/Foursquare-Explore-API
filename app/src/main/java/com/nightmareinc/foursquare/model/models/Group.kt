package com.nightmareinc.foursquare.model.models

import com.google.gson.annotations.SerializedName

data class Group (

    @field:SerializedName("items")
    val items: List<Item>

)