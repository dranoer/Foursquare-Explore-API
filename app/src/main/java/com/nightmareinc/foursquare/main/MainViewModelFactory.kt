package com.nightmareinc.foursquare.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.nightmareinc.foursquare.model.repositories.VenueRepository

class MainViewModelFactory (var venueRepository: VenueRepository) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(venueRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}