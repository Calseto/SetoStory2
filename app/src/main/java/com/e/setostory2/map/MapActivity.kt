package com.e.setostory2.map

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.e.setostory2.R
import com.e.setostory2.databinding.ActivityMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var gooMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel:MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("token", null)

        viewModel = ViewModelProvider(this)[MapViewModel::class.java]

        viewModel.getMarker("Bearer $token")

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gooMap = googleMap

        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isMapToolbarEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true

        }


        viewModel = ViewModelProvider(this)[MapViewModel::class.java]
        viewModel.observe().observe(this) {
            if (it == null) {
                Toast.makeText(this, "data doesnt exist", Toast.LENGTH_SHORT).show()
            }
            else{
                for (i in 0 until it.listStory.size) {
                    val mark = it.listStory[i].lon?.let { it1 ->
                        it.listStory[i].lat?.let { it2 ->
                            LatLng(
                                it2.toDouble(), it1.toDouble()
                            )
                        }
                    }
                    mark?.let { it1 ->
                        MarkerOptions()
                            .position(it1)
                            .title(it.listStory[i].description)
                            .snippet(it.listStory[i].name)
                    }
                        ?.let { it2 -> gooMap.addMarker(it2) }
                }
            }
        }
    }
}