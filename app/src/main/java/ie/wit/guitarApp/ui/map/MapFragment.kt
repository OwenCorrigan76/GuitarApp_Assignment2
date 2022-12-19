package ie.wit.guitarApp.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import ie.wit.guitarApp.R
import ie.wit.guitarApp.databinding.ContentGuitarMapsBinding
import ie.wit.guitarApp.databinding.FragmentMapBinding
import ie.wit.guitarApp.main.MainApp
import ie.wit.guitarApp.models.GuitarAppModel

class MapFragment : Fragment() {
    lateinit var app: MainApp
    lateinit var map: GoogleMap

    private var _fragBinding: FragmentMapBinding? = null
    private val fragBinding get() = _fragBinding!!
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
            /** inflate fragment_guitar */
            _fragBinding = FragmentMapBinding.inflate(inflater, container, false)
            /** Tie into the root (overall layout) */
            val root = fragBinding.root
            activity?.title = getString(R.string.action_maps)
            return root;
        }
    }


