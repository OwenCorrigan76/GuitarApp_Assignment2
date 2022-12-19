package ie.wit.guitarApp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.wit.guitarApp.databinding.FragmentGuitarDetailBinding
import ie.wit.guitarApp.ui.auth.LoggedInViewModel
import ie.wit.guitarApp.ui.list.ListViewModel
import timber.log.Timber

class GuitarDetailFragment : Fragment() {
    private lateinit var detailViewModel: GuitarDetailViewModel
    private val args by navArgs<GuitarDetailFragmentArgs>()
    private var _fragBinding: FragmentGuitarDetailBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private val listViewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentGuitarDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(GuitarDetailViewModel::class.java)
        detailViewModel.observableGuitar.observe(viewLifecycleOwner, Observer {
            render()
        })

        // for editing
        fragBinding.editGuitarButton.setOnClickListener {
            detailViewModel.updateGuitar(
                loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.guitarid, fragBinding.guitarvm?.observableGuitar!!.value!!
            )
            findNavController().navigateUp()
        }
       // for deleting
        fragBinding.deleteGuitarButton.setOnClickListener {
            listViewModel.delete(
                loggedInViewModel.liveFirebaseUser.value?.email!!,
                detailViewModel.observableGuitar.value?.uid!!
            )
            findNavController().navigateUp()
        }
        return root
    }

    private fun render() {
        fragBinding.editMake.setText("This is Make")
        fragBinding.editModel.setText("This is Model")
        fragBinding.editManDate.text
        fragBinding.guitarvm = detailViewModel
        Timber.i("Retrofit fragBinding.guitarvm == $fragBinding.guitarvm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getGuitar(
            loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.guitarid
        )
        /* detailViewModel.updateGuitar(
            loggedInViewModel.liveFirebaseUser.value?.uid!!,
            // two way dataBinding
            args.guitarid, fragBinding.guitarvm?.observableGuitar!!.value!!)
    }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}


