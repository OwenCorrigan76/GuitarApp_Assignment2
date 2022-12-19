package ie.wit.guitarApp.ui.guitar

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.guitarApp.R
import ie.wit.guitarApp.ui.map.MapActivity
import ie.wit.guitarApp.databinding.FragmentGuitarBinding
import ie.wit.guitarApp.firebase.FirebaseImageManager
import ie.wit.guitarApp.main.MainApp
import ie.wit.guitarApp.models.GuitarAppModel
import ie.wit.guitarApp.models.Location
import ie.wit.guitarApp.ui.auth.LoggedInViewModel
import ie.wit.guitarApp.ui.list.ListViewModel
import ie.wit.guitarApp.utils.readImageUri
import ie.wit.guitarApp.utils.showImagePicker
import timber.log.Timber
import timber.log.Timber.Forest.i

/** this lifeCycle is directly dependant on the Home (Activity) lifeCycle */
class GuitarFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentGuitarBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var guitarViewModel: GuitarViewModel
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    var location = Location(-34.0, 151.0, 15f)
    val guitars = GuitarAppModel()
    val today = Calendar.getInstance()
    val year = today.get(Calendar.YEAR)
    val month = today.get(Calendar.MONTH)
    val day = today.get(Calendar.DAY_OF_MONTH)
    var image: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** inflate fragment_guitar */
        _fragBinding = FragmentGuitarBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_guitar)

        setupMenu()
        registerImagePickerCallback()
        registerMapCallback()

        guitarViewModel = ViewModelProvider(this).get(GuitarViewModel::class.java)
        guitarViewModel.observableStatus.observe(viewLifecycleOwner, Observer { status ->
            status?.let { render(status) }
        })
        var guitarTypes = resources.getStringArray(R.array.guitar_make_list)
        var guitarMake = activity?.findViewById<TextView>(R.id.guitarMake).toString()

        val guitarMakeSpinner = fragBinding.spinnerGuitarMake
        val arrayAdapter = activity?.let {
            ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, guitarTypes)
        }
        guitarMakeSpinner.adapter = arrayAdapter
        fragBinding.spinnerGuitarMake.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // if (make != null) {
                val spinnerPosition = arrayAdapter!!.getPosition(guitarMake)
                guitarMakeSpinner.setSelection(spinnerPosition)
                //     }
                guitarMake = " ${guitarTypes.get(position)}"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                guitarMake = "please select a guitar make"
            }
        }

        /** map click listener */
        fragBinding.guitarLocation.setOnClickListener { // launch maps and pass location to MapActivity
            if (guitars.zoom != 0f) {
                location.lat = guitars.lat
                location.lng = guitars.lng
                location.zoom = guitars.zoom
            }

            val launcherIntent = Intent(activity, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

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
        setButtonListener(fragBinding)

        /** This allows us to select an image with chooseImage button */
        fragBinding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
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
                return NavigationUI.onNavDestinationSelected(
                    menuItem,
                    requireView().findNavController()
                )
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                }
            }
            false -> Toast.makeText(context, getString(R.string.guitarError), Toast.LENGTH_LONG)
                .show()
        }
    }

    /** Send to the model to be displayed in the list view */
    private fun setButtonListener(layout: FragmentGuitarBinding) {
        layout.addButton.setOnClickListener {
            val valuation = layout.valuePicker.value.toDouble()
            val guitarModel = layout.guitarModel.text.toString()
            val manufactureDate = layout.dateView.text.toString()
            var guitarMake = layout.spinnerGuitarMake.selectedItem.toString()

            print("*****************" + guitars + "*******************")

            guitarViewModel.addGuitar(
                loggedInViewModel.liveFirebaseUser,
                GuitarAppModel(
                    valuation = valuation,
                    guitarMake = guitarMake,
                    guitarModel = guitarModel,
                    manufactureDate = manufactureDate,
                    image = image.toString(),
                    email = loggedInViewModel.liveFirebaseUser.value?.email!!,
                    lat = location.lat,
                    lng = location.lng,
                    zoom = 20f,
                )
            )
            i(
                "add Button Pressed: ${
                    location.lat.toString() + " " + location.lng.toString() + " " +
                            15f + " " + guitarMake + " " + guitarModel + " " + valuation + " " +
                            manufactureDate + " " + " image is " + guitars.image
                }"
            )
        }
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i(
                                "Got Result ${
                                    readImageUri(
                                        result.resultCode,
                                        result.data
                                    ).toString()
                                }"
                            )
                            image = result.data!!.data!!.toString()
                            fragBinding.chooseImage.setText(R.string.change_guitar_image)
                            FirebaseImageManager
                                .updateGuitarImage(
                                    loggedInViewModel.liveFirebaseUser.value!!.uid,
                                    readImageUri(result.resultCode, result.data),
                                    fragBinding.guitarImage,
                                    true
                                )
                            guitars.image = result.data!!.data!!.toString()
                            println("this is an image ${guitars.image}")
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> {}
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
        val reportViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        reportViewModel.observableGuitarsList.observe(viewLifecycleOwner, Observer {
            fragBinding.progressBar.progress
        })
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            guitars.lat = location.lat
                            guitars.lng = location.lng
                            guitars.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}

