package com.nightmareinc.foursquare.database

import android.content.Context
import androidx.room.*

import com.nightmareinc.foursquare.model.models.Venue

@Database(entities = [Venue::class], version = 1, exportSchema = false)
abstract class VenueDatabase : RoomDatabase() {

    abstract val venueDatabaseDao: VenueDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: VenueDatabase? = null

        fun getInstance(context: Context) : VenueDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VenueDatabase::class.java,
                        "venue_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}