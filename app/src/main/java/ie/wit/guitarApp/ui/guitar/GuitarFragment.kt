package ie.wit.guitarApp.ui.guitar

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Resources
import android.icu.util.Calendar
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.squareup.picasso.Picasso
import ie.wit.guitarApp.R
import ie.wit.guitarApp.databinding.FragmentGuitarBinding
import ie.wit.guitarApp.halpers.showImagePicker
import ie.wit.guitarApp.main.MainApp
import ie.wit.guitarApp.models.GuitarModel
import timber.log.Timber.i

class GuitarFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentGuitarBinding? = null
    private val fragBinding get() = _fragBinding!!
    val guitars = GuitarModel()
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var guitarViewModel: GuitarViewModel
    //lateinit var navController: NavController
    val today = Calendar.getInstance()
    val year = today.get(Calendar.YEAR)
    val month = today.get(Calendar.MONTH)
    val day = today.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        // setHasOptionsMenu(true)
        // registerImagePickerCallback()

        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentGuitarBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_guitar)
        setupMenu()
        guitarViewModel =
            ViewModelProvider(this).get(GuitarViewModel::class.java)
        //val textView: TextView = root.findViewById(R.id.text_home)
        guitarViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        fragBinding.progressBar.max = 10000
        fragBinding.valuePicker.minValue = 500
        fragBinding.valuePicker.maxValue = 10000

        fragBinding.valuePicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            fragBinding.valueAmount.setText("$newVal")
        }
        fragBinding.dateButton.setOnClickListener {
            val dialogP = DatePickerDialog(
                requireContext(),
                { _, Year, Month, Day ->
                    val Month = Month + 1
                    fragBinding.dateView.setText("$Day/$Month/$Year")
                }, year, month, day
            )
            dialogP.show()
        }
        //  fun guitarMakeSpinner(layout: FragmentDonateBinding) {
        val guitarMakeSpinner = activity?.findViewById<Spinner>(R.id.spinnerGuitarMake)
        val make = activity?.findViewById<TextView>(R.id.guitarMake)
        val res: Resources = resources
        val types = res.getStringArray(R.array.guitar_make_list)
        if (guitarMakeSpinner == null) {
            print("This is null")
        }
        if (guitarMakeSpinner != null) {
            print("Value is null")
            val guitarAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, types)
            guitarMakeSpinner.adapter = guitarAdapter
            fragBinding.spinnerGuitarMake.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (make != null) {
                            val spinnerPosition =
                                guitarAdapter!!.getPosition(fragBinding.guitarMake.toString())
                            guitarMakeSpinner.setSelection(spinnerPosition)
                        }
                        make!!.text = " ${types.get(position)}"
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        make!!.text = "please select a guitar make"
                    }
                }
        }
        fragBinding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()
        setButtonListener(fragBinding)
        return root;
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_guitar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            GuitarFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener(layout: FragmentGuitarBinding) {
        layout.addButton.setOnClickListener {
            val valuation = layout.valuePicker.value.toDouble()
            val guitarMake = layout.guitarMake.text.toString()
            val guitarModel = layout.guitarModel.text.toString()
            val manufactureDate = layout.dateView.text.toString()


            app.guitarStore.create(
                GuitarModel(
                    valuation = valuation,
                    guitarMake = guitarMake,
                    guitarModel = guitarModel,
                    manufactureDate = manufactureDate,

                    )
            )
            i("add Button Pressed: ${guitars.guitarMake + guitars.guitarModel + guitars.valuation + guitars.manufactureDate + guitars.image}")
        }

    }


/*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_guitar, menu)
    super.onCreateOptionsMenu(menu, inflater)
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return NavigationUI.onNavDestinationSelected(
        item,
        requireView().findNavController()
    ) || super.onOptionsItemSelected(item)
}*/

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }
private fun registerImagePickerCallback() {
    imageIntentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    if (result.data != null) {
                        i("Got Result ${result.data!!.data}")
                        guitars.image = result.data!!.data!!
                        Picasso.get()
                            .load(guitars.image)
                            .into(fragBinding.guitarImage)
                        fragBinding.chooseImage.setText(R.string.change_guitar_image)
                    }
                }
                RESULT_CANCELED -> {}
                else -> {}
            }
        }
}

override fun onDestroyView() {
    super.onDestroyView()
    _fragBinding = null
}

override fun onResume() {
    super.onResume()
}
}