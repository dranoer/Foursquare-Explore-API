package com.nightmareinc.foursquare.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import com.nightmareinc.foursquare.model.models.Item
import com.nightmareinc.foursquare.model.repositories.VenueRepository
import com.nightmareinc.foursquare.util.SingleLiveData
import com.nightmareinc.foursquare.util.Status

class MainViewModel (var venueRepository: VenueRepository) : ViewModel() {

    private var venueList: List<Item>? = null

    private var _venues = MutableLiveData<List<Item>>()
    val venues: LiveData<List<Item>>?
        get() = _venues

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    // Get list of venues
    fun getVenues(latlng: String) {
        viewModelScope.launch {
            try {
                venueList = venueRepository.fetchVenues(latlng).response.groups[0].items
                _status.value = Status.LOADING

                _venues.value = venueList
                _status.value = Status.DONE

                for (item in venueList!!) {
                    venueRepository.insertVenue(item.venue)
                }

            } catch (e: Exception) {
                _status.value = Status.ERROR
                Log.d("nazi", "Error >> ${_status?.value} >> ${e.message} ")
                e.printStackTrace()
            }
        }
    }

    // Navigate to venue's detail screen
    val navigateToDetail = SingleLiveData.SingleLiveEvent<String>()

    fun onVenueClicked(id: String?) {
        navigateToDetail.value = id
    }

}