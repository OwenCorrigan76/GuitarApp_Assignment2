package ie.wit.guitarApp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.guitarApp.models.GuitarManager
import ie.wit.guitarApp.models.GuitarModel
import ie.wit.guitarApp.ui.guitar.GuitarViewModel

class GuitarDetailViewModel : ViewModel() {
  private val guitar = MutableLiveData<GuitarModel>() // keeps track of individual guitars

// public read only observable data
   val observableGuitar: LiveData<GuitarModel>
    get() = guitar

    // this function gets an id and updates the guitar.value via GuitarManager interface
    fun getGuitar(id: Long){
        guitar.value = GuitarManager.findById(id)
    }
}