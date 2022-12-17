package ie.wit.guitarApp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.guitarApp.R
import ie.wit.guitarApp.models.Location
import org.wit.guitar.activities.GuitarMapsActivity

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    var location = Location()
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        location = intent.extras?.getParcelable<Location>("location")!!
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_map -> {
                val launcherIntent = Intent(this, GuitarMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Placemark")
            .snippet("GPS : $loc")
            .draggable(true)
            .position(loc)
        map.setOnMarkerClickListener(this)
        map.setOnMarkerDragListener(this)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    override fun onMarkerDrag(marker: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom    }

    override fun onMarkerDragStart(marker: Marker) {
        TODO("Not yet implemented")
    }
    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        val loc = LatLng(location.lat, location.lng)
        marker.snippet = "GPS : $loc"
        return false
    }

}
