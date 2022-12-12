package ie.wit.guitarApp.ui.detail
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ie.wit.guitarApp.R
import ie.wit.guitarApp.databinding.FragmentGuitarBinding
import ie.wit.guitarApp.databinding.FragmentGuitarDetailBinding
import ie.wit.guitarApp.ui.guitar.GuitarViewModel
import timber.log.Timber


class GuitarDetailFragment : Fragment() {
    private lateinit var detailViewModel: GuitarDetailViewModel
    private val args by navArgs<GuitarDetailFragmentArgs>()
    private var _fragBinding: FragmentGuitarDetailBinding? = null
    private val fragBinding get() = _fragBinding!!
   // private lateinit var viewModel: GuitarDetailViewModel

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
        detailViewModel.getGuitar(args.guitarid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
    }


