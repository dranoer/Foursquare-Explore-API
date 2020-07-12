package com.nightmareinc.foursquare.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import com.nightmareinc.foursquare.R
import com.nightmareinc.foursquare.database.VenueDatabase
import com.nightmareinc.foursquare.databinding.FragmentMainBinding
import com.nightmareinc.foursquare.model.api.RequestInterceptor
import com.nightmareinc.foursquare.model.api.VenueAPI
import com.nightmareinc.foursquare.model.repositories.VenueRepository
import com.nightmareinc.foursquare.util.PermissionUtils

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var mainViewModel: MainViewModel

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 999
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = VenueDatabase.getInstance(application).venueDatabaseDao
        val viewModelFactory = MainViewModelFactory(VenueRepository(dataSource, VenueAPI.invoke(RequestInterceptor())))

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this

        val manager = LinearLayoutManager(context)
        binding.venueList.layoutManager = manager

        val adapter = VenueAdapter(VenueListener { venueId ->
            mainViewModel.onVenueClicked(venueId)
        })
        binding.venueList.adapter = adapter

        // Setup the list of venues
        mainViewModel.venues?.observe(this, Observer { venues ->
            venues.let {
                adapter.submitList(venues)
            }
        })

        mainViewModel.navigateToDetail.observe(this, Observer { venue ->
            venue?.let {
                Log.d("clicked", "id ${venue}")
                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(venue)
                )
            }
        })

        return binding.root
    }

    private fun setUpLocationListener() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(5000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        if (ActivityCompat.checkSelfPermission(
                activity!!.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity!!.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
                        val sb = StringBuilder()
                        sb.append(location.latitude)
                        sb.append(",")
                        sb.append(location.longitude)

                        mainViewModel.getVenues(sb.toString())
                        binding.progressBar.visibility = View.GONE
                    }
                    // Few more things we can do here:
                    // For example: Update the location of user on server
                }
            },
            Looper.myLooper()
        )
    }

    override fun onStart() {
        super.onStart()

        when {
            PermissionUtils.isAccessFineLocationGranted(activity!!.applicationContext) -> {
                when {
                    PermissionUtils.isLocationEnabled(activity!!.applicationContext) -> {
                        setUpLocationListener()
                    }
                    else -> {
                        PermissionUtils.showGPSNotEnabledDialog(activity!!.applicationContext)
                    }
                }
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    activity as AppCompatActivity,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(activity!!.applicationContext) -> {
                            setUpLocationListener()
                        }
                        else -> {
                            PermissionUtils.showGPSNotEnabledDialog(activity!!.applicationContext)
                        }
                    }
                } else {
                    Toast.makeText(
                        activity!!.applicationContext,
                        getString(R.string.location_permission_not_granted),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}