package ie.wit.guitarApp.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.guitarApp.R
import ie.wit.guitarApp.adapters.GuitarAdapter
import ie.wit.guitarApp.adapters.GuitarClickListener
import ie.wit.guitarApp.databinding.FragmentListBinding

import ie.wit.guitarApp.main.MainApp
import ie.wit.guitarApp.models.GuitarModel
import ie.wit.guitarApp.ui.auth.LoggedInViewModel
import ie.wit.guitarApp.utils.*

class ListFragment : Fragment(), GuitarClickListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader: AlertDialog
    private val listViewModel: ListViewModel by activityViewModels()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   app = activity?.application as MainApp
        //  setHasOptionsMenu(true)
        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        //    activity?.title = getString(R.string.action_list)
        setupMenu()
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        fragBinding.fab.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToGuitarFragment()
            findNavController().navigate(action)
        }
        showLoader(loader, "Downloading Guitars")
        listViewModel.observableGuitarsList.observe(viewLifecycleOwner, Observer { guitars ->
            guitars?.let {
                render(guitars as ArrayList<GuitarModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })
        /*  // MVVM in action here
           listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
           listViewModel.observableGuitarsList.observe(viewLifecycleOwner, Observer { guitars ->
               guitars?.let { render(guitars) }
           })
   */

      /*  val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToGuitarFragment()
            findNavController().navigate(action)
        }
*/
        // for swipe delete
        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader, "Deleting Guitar")
                val adapter = fragBinding.recyclerView.adapter as GuitarAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                listViewModel.delete(
                    listViewModel.liveFirebaseUser.value?.email!!,
                    (viewHolder.itemView.tag as GuitarModel)._id
                )
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        // for swipe edit
        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onGuitarClick(viewHolder.itemView.tag as GuitarModel)
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

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu)
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

    private fun render(guitarsList: ArrayList<GuitarModel>) { // live data values that have been updated
        fragBinding.recyclerView.adapter = GuitarAdapter(guitarsList, this) // pass in the list
        if (guitarsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.guitarsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.guitarsNotFound.visibility = View.GONE
        }
    }

    // floating action takes us directly to the guitar fragment via the navigation component
    override fun onGuitarClick(guitar: GuitarModel) {
        val action = ListFragmentDirections.actionListFragmentToGuitarDetailFragment(guitar._id)
        findNavController().navigate(action)
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader, "Downloading Guitars")
            listViewModel.load()

        }
    }

    private fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "Downloading Donations")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                listViewModel.liveFirebaseUser.value = firebaseUser
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