package ie.wit.guitarApp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import ie.wit.guitarApp.R


class GuitarDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GuitarDetailFragment()
    }

    private lateinit var viewModel: GuitarDetailViewModel
    private val args by navArgs<GuitarDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_guitar_detail, container, false)

        Toast.makeText(context,"Guitar ID Selected : ${args.guitarid}",Toast.LENGTH_LONG).show()

        return view
    }


}