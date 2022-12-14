package ie.wit.guitarApp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
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

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    // .value property of <List<GuitarModel>> is equivalent to GuitarManager interface findAll function
    fun load() {
        try {// liveFirebaseUser for retrieving email users
            GuitarManager.findAll(liveFirebaseUser.value?.email!!, guitarList)
            Timber.i("Report Load Success : ${guitarList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }
    fun delete(email: String, id: String) {
        try {
            GuitarManager.delete(email, id)
            Timber.i("Delete Success")
        }
        catch (e: java.lang.Exception) {
            Timber.i("Delete Error : $e.message")
        }
    }
}