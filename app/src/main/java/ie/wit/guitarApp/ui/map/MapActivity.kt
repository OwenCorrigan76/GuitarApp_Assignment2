package ie.wit.guitarApp.ui.map

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.guitarApp.R
import ie.wit.guitarApp.models.GuitarAppModel
import ie.wit.guitarApp.models.Location
import timber.log.Timber

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    var location = Location()
    var guitars = GuitarAppModel()
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        location = intent.extras?.getParcelable<Location>("location")!!
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // val sydney = LatLng(-34.0, 151.0)
        val loc = LatLng(location.lat, location.lng)
           val options = MarkerOptions()
                .title("Placemark")
                .snippet("GPS : $loc")
                .draggable(true)
                .position(loc)
            map.setOnMarkerClickListener(this)
            map.setOnMarkerDragListener(this)
            map.addMarker(options)
        map.addMarker(MarkerOptions().position(loc).title("Default"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    override fun onMarkerDrag(marker: Marker) {
        Timber.i("Dragging Marker")
    }

    override fun onMarkerDragStart(marker: Marker) {
        
    }

    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // Showing co-ordinates on marker click
        val loc = LatLng(location.lat, location.lng)
        println(loc)
        marker.snippet = "GPS : $loc"
        return false
    }

    /** This intercepts the marker location when back button is pressed */
    override fun onBackPressed() {
        val resultIntent = Intent()
        /** Passing the new location upon back click */
        resultIntent.putExtra("location", location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }
}
