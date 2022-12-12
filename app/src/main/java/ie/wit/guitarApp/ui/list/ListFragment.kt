package ie.wit.guitarApp.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
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
import ie.wit.guitarApp.utils.SwipeToDeleteCallback
import ie.wit.guitarApp.utils.createLoader
import ie.wit.guitarApp.utils.hideLoader
import ie.wit.guitarApp.utils.showLoader

class ListFragment : Fragment(), GuitarClickListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var listViewModel: ListViewModel
    lateinit var loader : AlertDialog

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
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        showLoader(loader,"Downloading Guitars")
        listViewModel.observableGuitarsList.observe(viewLifecycleOwner, Observer {
                guitars ->
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


        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToGuitarFragment()
            findNavController().navigate(action)
        }
        return root
    }*/
        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragBinding.recyclerView.adapter as GuitarAdapter
                adapter.removeAt(viewHolder.adapterPosition)

            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)
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
            showLoader(loader,"Downloading Guitars")
            listViewModel.load()

        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        listViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}