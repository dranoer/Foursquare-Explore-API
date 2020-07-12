package com.nightmareinc.foursquare.model.repositories

import com.nightmareinc.foursquare.database.VenueDatabaseDao
import com.nightmareinc.foursquare.model.api.SafeApiRequest
import com.nightmareinc.foursquare.model.api.VenueAPI
import com.nightmareinc.foursquare.model.models.FoursquareResponse
import com.nightmareinc.foursquare.model.models.Venue

class VenueRepository(
    val database: VenueDatabaseDao,
    private val venueAPI: VenueAPI) : SafeApiRequest() {

    suspend fun fetchVenues(latlng: String?): FoursquareResponse {
        return apiRequest { venueAPI.getAllVenues(
            latlng
        ) }
    }

    suspend fun insertVenue(venue: Venue) {
        database.insert(venue)
    }

}