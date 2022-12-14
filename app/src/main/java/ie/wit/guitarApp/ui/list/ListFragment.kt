package ie.wit.guitarApp.ui.list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.guitarApp.R
import ie.wit.guitarApp.adapters.GuitarAdapter
import ie.wit.guitarApp.adapters.GuitarClickListener
import ie.wit.guitarApp.databinding.FragmentListBinding
import ie.wit.guitarApp.main.MainApp
import ie.wit.guitarApp.models.GuitarAppModel
import ie.wit.guitarApp.ui.auth.LoggedInViewModel
import ie.wit.guitarApp.utils.*

class ListFragment : Fragment(), GuitarClickListener {
    lateinit var app: MainApp
    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader: AlertDialog
    private val listViewModel: ListViewModel by activityViewModels()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    val guitars = GuitarAppModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragBinding.fab.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToGuitarFragment()
            findNavController().navigate(action)
        }
        showLoader(loader, "Downloading Guitars")
        listViewModel.observableGuitarsList.observe(viewLifecycleOwner, Observer
        // when guitar changes through observable....
        { guitars ->
            guitars?.let {
                // call render on the data (ArrayList)
                render(guitars as ArrayList<GuitarAppModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })
        setSwipeRefresh()

        // for swipe delete
        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader, "Deleting Guitar")
                val adapter = fragBinding.recyclerView.adapter as GuitarAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                // delete with the id assigned by Firebase
                listViewModel.delete(
                    listViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as GuitarAppModel).uid
                )
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        // for swipe edit
        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onGuitarClick(viewHolder.itemView.tag as GuitarAppModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            @SuppressLint("SetTextI18n")
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu)
                val item = menu.findItem(R.id.toggleGuitars) as MenuItem
                item.setActionView(R.layout.togglebutton_layout)
                val toggleGuitars: SwitchCompat =
                    item.actionView!!.findViewById(R.id.toggleButton)
                toggleGuitars.isChecked = false

                toggleGuitars.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) listViewModel.loadAll()
                    else listViewModel.load()
                }



                /** Setting up Dark Mode */
                val switch = menu.findItem(R.id.toggleDarkMode) as MenuItem
                switch.setActionView(R.layout.togglebutton_layout1)
                val toggleMode: SwitchCompat =
                    switch.actionView!!.findViewById(R.id.toggleButton1)
                toggleMode.isChecked = false
                /** Toggle switch n yje menu */
                toggleMode.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        toggleMode.text = "Dark Mode"
                        println("disable")
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        toggleMode.text = "Light Mode"
                        println("enable")
                    }
                }
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

    private fun render(guitarsList: ArrayList<GuitarAppModel>) { // live data values that have been updated
        // create an adapter and pass in the list
        fragBinding.recyclerView.adapter =
            listViewModel.readOnly.value?.let {
                GuitarAdapter(
                    guitarsList,
                    this,
                    it
                )
            } // pass in the list
        if (guitarsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.guitarsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.guitarsNotFound.visibility = View.GONE
        }
    }

    // floating action takes us directly to the guitar fragment via the navigation component
    override fun onGuitarClick(guitar: GuitarAppModel) {
        val action = ListFragmentDirections.actionListFragmentToGuitarDetailFragment(guitar.uid)
        findNavController().navigate(action)
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader, "Downloading Guitars")
            if (listViewModel.readOnly.value!!)
                listViewModel.loadAll()
            else
                listViewModel.load()
        }
    }

    private fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "Downloading Guitars")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                // update from the database
                listViewModel.liveFirebaseUser.value = firebaseUser
                // and reload with the correct data from a particular user
                listViewModel.load()
            }
        })
        //hideLoader(loader)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }


}