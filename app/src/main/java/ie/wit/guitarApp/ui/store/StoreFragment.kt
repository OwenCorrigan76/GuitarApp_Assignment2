package ie.wit.guitarApp.ui.store

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ie.wit.guitarApp.databinding.FragmentStoreBinding
import ie.wit.guitarApp.main.MainApp
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import ie.wit.guitarApp.R

class StoreFragment : Fragment() {
    lateinit var app: MainApp
    private lateinit var fragBinding: FragmentStoreBinding
    private lateinit var sharedPrefs: SharedPreferences


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
        // val switchCompat = fragBinding.switchCompat
     /*   val switch = fragBinding.switch1

        if (switch.isActivated) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            switch.text = "Disable dark Mode"
            println("disable")
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            switch.text = "Enable dark mode"
            println("enable")
        }
*/
    }
}

