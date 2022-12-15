package ie.wit.guitarApp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.guitarApp.firebase.FirebaseDBManager
import ie.wit.guitarApp.models.GuitarAppModel
import timber.log.Timber
import java.lang.Exception

class ListViewModel : ViewModel() {

    private val guitarList = MutableLiveData<List<GuitarAppModel>>()
    var readOnly = MutableLiveData(false)
    /** expose the public observable GuitarList */
    val observableGuitarsList: LiveData<List<GuitarAppModel>>
        get() = guitarList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    // .value property of <List<GuitarAppModel>> is equivalent to GuitarManager interface findAll function
    fun load() {
        try {// liveFirebaseUser for retrieving email users
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, guitarList)
            Timber.i("Report Load Success : ${guitarList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(guitarList)
            Timber.i("Report LoadAll Success : ${guitarList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report LoadAll Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}