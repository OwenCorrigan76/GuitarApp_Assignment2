package ie.wit.guitarApp.ui.guitar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.guitarApp.models.GuitarManager
import ie.wit.guitarApp.models.GuitarModel

class GuitarViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
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