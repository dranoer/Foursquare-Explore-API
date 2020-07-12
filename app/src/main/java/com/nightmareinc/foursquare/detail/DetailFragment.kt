package com.nightmareinc.foursquare.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.nightmareinc.foursquare.R
import com.nightmareinc.foursquare.database.VenueDatabase
import com.nightmareinc.foursquare.databinding.FragmentDetailBinding
import com.nightmareinc.foursquare.model.api.RequestInterceptor
import com.nightmareinc.foursquare.model.api.VenueAPI
import com.nightmareinc.foursquare.model.repositories.VenueRepository

class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )

        val application = requireNotNull(this.activity).application

        val arguments = DetailFragmentArgs.fromBundle(arguments!!)

        val dataSource = VenueDatabase.getInstance(application).venueDatabaseDao

        val viewModelFactory = DetailViewModelFactory(arguments.id, VenueRepository(dataSource, VenueAPI.invoke(
            RequestInterceptor()
        )))

        val detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)

        binding.detailViewModel = detailViewModel
        binding.lifecycleOwner = this

        detailViewModel.viewVenue!!.observe(this, Observer {
            it.let {
                binding.venueName.text = it.name
            }

        })

        return binding.root
    }

}