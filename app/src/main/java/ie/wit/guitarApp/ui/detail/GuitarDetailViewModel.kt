package ie.wit.guitarApp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.guitarApp.firebase.FirebaseDBManager
import ie.wit.guitarApp.models.GuitarAppModel
import timber.log.Timber

class GuitarDetailViewModel : ViewModel() {
    private val guitar = MutableLiveData<GuitarAppModel>() // keeps track of individual guitars

    // public read only observable data
    var observableGuitar: LiveData<GuitarAppModel>
        get() = guitar
        set(value) {guitar.value = value.value}

    // this function gets an id and updates the guitar.value via GuitarManager interface
    fun getGuitar(userid:String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, guitar)
            Timber.i("Detail getGuitar() Success : ${
                guitar.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getDonation() Error : $e.message")
        }
    }
    fun updateGuitar(userid:String, id: String,guitar: GuitarAppModel) {
        try {
            FirebaseDBManager.update(userid, id, guitar)
            Timber.i("Detail update() Success : $guitar")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}


