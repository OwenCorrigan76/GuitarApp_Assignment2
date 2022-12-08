package ie.wit.guitarApp.ui.list

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.guitarApp.R
import ie.wit.guitarApp.adapters.GuitarAdapter
import ie.wit.guitarApp.adapters.GuitarListener
import ie.wit.guitarApp.databinding.FragmentListBinding

import ie.wit.guitarApp.main.MainApp
import ie.wit.guitarApp.models.GuitarModel

class ListFragment : Fragment(), GuitarListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var listViewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   app = activity?.application as MainApp
        setHasOptionsMenu(true)
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
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
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

    private fun render(guitarsList: List<GuitarModel>) {
        fragBinding.recyclerView.adapter = GuitarAdapter(guitarsList)
        if (guitarsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.guitarsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.guitarsNotFound.visibility = View.GONE
        }
    }
    override fun onGuitarClick(guitar: GuitarModel) {
        val action = ListFragmentDirections.actionListFragmentToGuitarDetailFragment(guitar.id)
        findNavController().navigate(action)
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