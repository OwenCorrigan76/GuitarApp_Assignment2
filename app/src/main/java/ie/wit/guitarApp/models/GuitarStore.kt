package ie.wit.guitarApp.models
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface GuitarStore {
    fun findAll(guitarList:
                MutableLiveData<List<GuitarAppModel>>)
    fun findAll(userid:String,
                guitarList:
                MutableLiveData<List<GuitarAppModel>>)
    fun findAll() : List<GuitarAppModel>
    fun findById(userid:String, guitarid: String,
                 guitar: MutableLiveData<GuitarAppModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, guitar: GuitarAppModel)
    fun delete(userid:String, guitarid: String)
    fun update(userid:String, guitarid: String, guitar: GuitarAppModel)
}
