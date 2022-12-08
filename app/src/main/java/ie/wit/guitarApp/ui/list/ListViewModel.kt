package ie.wit.guitarApp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.guitarApp.models.GuitarManager
import ie.wit.guitarApp.models.GuitarModel

class ListViewModel : ViewModel() {

    private val guitarsList = MutableLiveData<List<GuitarModel>>()

    val observableGuitarsList: LiveData<List<GuitarModel>>
        get() = guitarsList

    init {
        load()
    }

    fun load() {
        guitarsList.value = GuitarManager.findAll()
    }
}