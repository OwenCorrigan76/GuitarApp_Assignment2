package org.wit.guitar.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import ie.wit.guitarApp.R
import ie.wit.guitarApp.databinding.ActivityGuitarMapsBinding
import ie.wit.guitarApp.databinding.ContentGuitarMapsBinding
import ie.wit.guitarApp.main.MainApp
import ie.wit.guitarApp.models.GuitarAppModel
import ie.wit.guitarApp.ui.map.MapFragment


class GuitarMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener{
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityGuitarMapsBinding
    private lateinit var contentBinding: ContentGuitarMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp
    val guitars = GuitarAppModel()
    val fragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment
    var liveFirebaseUser = MutableLiveData<FirebaseUser>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityGuitarMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentGuitarMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync{
            map = it
       //     configureMap()
        }
    }
  /* fun configureMap() {

        map.setOnMarkerClickListener(this)
        map.uiSettings.isZoomControlsEnabled = true

        app.guitarStore.findAll(liveFirebaseUser, MutableLiveData<List<GuitarAppModel>>()){
            val loc = LatLng(guitars.lat, guitars.lng)
            val options = MarkerOptions().title(guitars.guitarMake).position(loc)
            map.addMarker(options)?.tag = guitars.uid
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, guitars.zoom))
        }
    }*/

    override fun onMarkerClick(marker: Marker): Boolean {
        val currentTitle: TextView = findViewById(R.id.currentTitle)
        currentTitle.text = marker.title

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