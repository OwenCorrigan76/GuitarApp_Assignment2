package ie.wit.guitarApp.ui.store

import android.R
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ie.wit.guitarApp.databinding.FragmentGuitarBinding
import ie.wit.guitarApp.databinding.FragmentStoreBinding
import ie.wit.guitarApp.main.MainApp


class StoreFragment : Fragment() {
    lateinit var app: MainApp
    private lateinit var fragBinding: FragmentStoreBinding
  //  private val fragBinding get() = _fragBinding!!
    private lateinit var storeViewModel: StoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
fragBinding = FragmentStoreBinding.inflate(layoutInflater)

        return fragBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        fragBinding.linkText.setText("guitars.com")
        fragBinding.linkText2.setText("thomann.de")
        fragBinding.linkText3.setText("musicStore.de")
    }
}