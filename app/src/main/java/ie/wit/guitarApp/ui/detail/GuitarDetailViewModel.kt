package ie.wit.guitarApp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.guitarApp.models.GuitarManager
import ie.wit.guitarApp.models.GuitarModel
import ie.wit.guitarApp.ui.guitar.GuitarViewModel
import timber.log.Timber

class GuitarDetailViewModel : ViewModel() {
    private val guitar = MutableLiveData<GuitarModel>() // keeps track of individual guitars

    // public read only observable data
    var observableGuitar: LiveData<GuitarModel>
        get() = guitar
        set(value) {guitar.value = value.value}

    // this function gets an id and updates the guitar.value via GuitarManager interface
    fun getGuitar(email: String, id: String) {
        try {
            GuitarManager.findById(email, id, guitar)
            Timber.i("Detail getDonation() Success : ${guitar.value.toString()}")
        } catch (e: Exception) {
            Timber.i("Detail getDonation() Error : $e.message")
        }
    }
    fun updateGuitar(email:String, id: String,guitar: GuitarModel) {
        try {
            GuitarManager.update(email, id, guitar)
            Timber.i("Detail update() Success : $guitar")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}
