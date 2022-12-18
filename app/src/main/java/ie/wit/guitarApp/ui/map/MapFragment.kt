package ie.wit.guitarApp.ui.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

import ie.wit.guitarApp.R
import ie.wit.guitarApp.databinding.ContentGuitarMapsBinding
import ie.wit.guitarApp.databinding.FragmentMapBinding
import ie.wit.guitarApp.main.MainApp
import ie.wit.guitarApp.models.GuitarAppModel

class MapFragment : Fragment() {
    val listView: View? = activity?.findViewById(R.id.mapView)
    lateinit var app: MainApp
    lateinit var map: GoogleMap

    private var _fragBinding: FragmentMapBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    val guitars = GuitarAppModel()
    private lateinit var contentBinding: ContentGuitarMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      /*  contentBinding = ContentGuitarMapsBinding.bind(fragBinding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            map = it
        }*/
            /** inflate fragment_guitar */
            _fragBinding = FragmentMapBinding.inflate(inflater, container, false)
            /** Tie into the root (overall layout) */
            val root = fragBinding.root
            activity?.title = getString(R.string.action_maps)

            return root;
        }

   /*   override  fun onMarkerClick(marker: Marker): Boolean {
            val currentTitle: TextView = requireActivity().findViewById(R.id.currentTitle)
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
        }*/

    }


