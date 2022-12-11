package ie.wit.guitarApp.ui.guitar

/* most of the data will end up here */

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.guitarApp.models.GuitarManager
import ie.wit.guitarApp.models.GuitarModel

class GuitarViewModel : ViewModel() { // declaration of type ViewModel

    private val status = MutableLiveData<Boolean>()

    //public observable status - can't be changed (read only)
    val observableStatus: LiveData<Boolean>
    // get at and return status
        get() = status

    fun addGuitar(guitar: GuitarModel) {
        status.value = try {
            GuitarManager.create(guitar)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}