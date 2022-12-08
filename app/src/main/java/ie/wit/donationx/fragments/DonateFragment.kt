package ie.wit.donationx.fragments

import android.app.DatePickerDialog
import android.content.res.Resources
import android.icu.util.Calendar
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.donationx.R
import ie.wit.donationx.databinding.FragmentDonateBinding
import ie.wit.donationx.main.DonationXApp
import ie.wit.donationx.models.DonationModel
import timber.log.Timber.i

class DonateFragment : Fragment() {

    lateinit var app: DonationXApp
    private var _fragBinding: FragmentDonateBinding? = null
    private val fragBinding get() = _fragBinding!!
    val guitars = DonationModel()

    //lateinit var navController: NavController
    val today = Calendar.getInstance()
    val year = today.get(Calendar.YEAR)
    val month = today.get(Calendar.MONTH)
    val day = today.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as DonationXApp
        setHasOptionsMenu(true)
        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentDonateBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_donate)

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
        if (guitarMakeSpinner != null) {
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
        setButtonListener(fragBinding)
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DonateFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener(layout: FragmentDonateBinding) {
        layout.addButton.setOnClickListener {

            val valuation = layout.valuePicker.value.toDouble()
            val guitarMake = layout.spinnerGuitarMake.toString()
            val guitarModel = layout.guitarModel.text.toString()
            val manufactureDate = layout.dateView.text.toString()
            app.donationsStore.create(
                DonationModel(
                    valuation = valuation,
                    guitarMake = guitarMake,
                    guitarModel = guitarModel,
                    manufactureDate = manufactureDate
                )
            )
            i("add Button Pressed: ${guitars.guitarMake + guitars.guitarModel + guitars.valuation + guitars.manufactureDate}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_donate, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
    }
}