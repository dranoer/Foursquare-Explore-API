package com.nightmareinc.foursquare.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

import com.nightmareinc.foursquare.model.repositories.VenueRepository

class DetailViewModelFactory (
    val id: String,
    var venueRepository: VenueRepository) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(id, venueRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}