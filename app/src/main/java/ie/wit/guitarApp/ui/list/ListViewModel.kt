package ie.wit.guitarApp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.guitarApp.models.GuitarManager
import ie.wit.guitarApp.models.GuitarManager.findAll
import ie.wit.guitarApp.models.GuitarModel
import timber.log.Timber
import java.lang.Exception

class ListViewModel : ViewModel() {

    private val guitarList = MutableLiveData<List<GuitarModel>>()

    /** expose the public observable GuitarList */
    val observableGuitarsList: LiveData<List<GuitarModel>>
        get() = guitarList

    init {
        load()
    }

    // .value property of <List<GuitarModel>> is equivalent to GuitarManager interface findAll function
    fun load() {
        try {
           GuitarManager.findAll(guitarList)
            Timber.i("Retrofit Success : $guitarList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }
    fun delete(id: String) {
        try {
            GuitarManager.delete(id)
            GuitarManager.findAll(guitarList)
            Timber.i("Retrofit Delete Success")
        }
        catch (e: java.lang.Exception) {
            Timber.i("Retrofit Delete Error : $e.message")
        }
    }
}