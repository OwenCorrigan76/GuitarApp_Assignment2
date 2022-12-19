package ie.wit.guitarApp.ui.store

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ie.wit.guitarApp.databinding.FragmentStoreBinding
import ie.wit.guitarApp.main.MainApp

class StoreFragment : Fragment() {
    lateinit var app: MainApp
    private lateinit var fragBinding: FragmentStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragBinding = FragmentStoreBinding.inflate(layoutInflater)

        return fragBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragBinding.linkText.setText("guitars.com")
        fragBinding.linkText2.setText("thomann.de")
        fragBinding.linkText3.setText("musicstore.de")
    }
}