package com.nightmareinc.foursquare.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import com.nightmareinc.foursquare.model.models.Venue
import com.nightmareinc.foursquare.model.repositories.VenueRepository

class DetailViewModel (
    val id: String,
    var venueRepository: VenueRepository) : ViewModel() {

    val _viewVenue = MutableLiveData<Venue>()
    val viewVenue: LiveData<Venue>?
        get() = _viewVenue

    init {
        viewModelScope.launch {
            val venue = venueRepository.database.get(id)
            _viewVenue.value = venue
        }
    }

}