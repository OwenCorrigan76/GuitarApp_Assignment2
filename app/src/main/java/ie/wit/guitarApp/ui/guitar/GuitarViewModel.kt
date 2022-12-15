package ie.wit.guitarApp.ui.guitar

/* most of the data will end up here */

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.guitarApp.firebase.FirebaseDBManager
import ie.wit.guitarApp.firebase.FirebaseImageManager
import ie.wit.guitarApp.models.GuitarAppModel

class GuitarViewModel : ViewModel() { // declaration of type ViewModel

    private val status = MutableLiveData<Boolean>()

    //public observable status - can't be changed (read only)
    val observableStatus: LiveData<Boolean>
        // get at and return status
        get() = status

    fun addGuitar(
        firebaseUser: MutableLiveData<FirebaseUser>,
        guitar: GuitarAppModel
    ) {
        status.value = try {
            guitar.profilepic = FirebaseImageManager.imageUri.value.toString()
          //  guitar.image = FirebaseImageManager.imageUri.value.toString()

            FirebaseDBManager.create(firebaseUser, guitar)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}