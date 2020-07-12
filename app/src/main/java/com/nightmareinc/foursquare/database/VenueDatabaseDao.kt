package com.nightmareinc.foursquare.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.nightmareinc.foursquare.model.models.Venue

@Dao
interface VenueDatabaseDao {

    @Insert
    suspend fun insert(venue: Venue): Long

    @Update
    suspend fun update(venue: Venue)

    @Query("SELECT * FROM venue_table WHERE id = :id")
    suspend fun get(id: String): Venue

    @Query("SELECT * FROM venue_table ORDER BY id DESC")
    fun getAllVenues(): LiveData<List<Venue>>

}