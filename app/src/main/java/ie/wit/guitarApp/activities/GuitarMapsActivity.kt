package org.wit.guitar.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.guitarApp.R
import ie.wit.guitarApp.databinding.ActivityGuitarMapsBinding
import ie.wit.guitarApp.databinding.ContentGuitarMapsBinding
import ie.wit.guitarApp.main.MainApp


class GuitarMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityGuitarMapsBinding
    private lateinit var contentBinding: ContentGuitarMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityGuitarMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        contentBinding = ContentGuitarMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }



    fun configureMap() {
        map.setOnMarkerClickListener(this)
        map.uiSettings.setZoomControlsEnabled(true)
        app.guitarStore.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.guitarMake).title(it.guitarMake).position(loc)
            map.addMarker(options)?.tag = it.uid
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        contentBinding.currentTitle.text = marker.title
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}