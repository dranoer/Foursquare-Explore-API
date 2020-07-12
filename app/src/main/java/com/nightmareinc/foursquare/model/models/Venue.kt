package com.nightmareinc.foursquare.model.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "venue_table")
data class Venue (

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: String,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String? = null

//    @field:SerializedName("location")
//    val location: VenueLocation? = null

)