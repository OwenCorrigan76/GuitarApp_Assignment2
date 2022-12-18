package ie.wit.guitarApp.ui.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.wit.guitarApp.R
import ie.wit.guitarApp.activities.MapActivity
import ie.wit.guitarApp.databinding.FragmentGuitarBinding
import ie.wit.guitarApp.databinding.FragmentMapBinding
import ie.wit.guitarApp.main.MainApp
import ie.wit.guitarApp.models.GuitarAppModel
import timber.log.Timber

class MapFragment : Fragment() {
    val listView: View? = activity?.findViewById(R.id.mapView)
    lateinit var app: MainApp
    private var _fragBinding: FragmentMapBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    val guitars = GuitarAppModel()

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

        setButtonListener(fragBinding)
        return root;
    }

    private fun setButtonListener(layout: FragmentMapBinding) {

            Timber.i("**************")
        }
    }

