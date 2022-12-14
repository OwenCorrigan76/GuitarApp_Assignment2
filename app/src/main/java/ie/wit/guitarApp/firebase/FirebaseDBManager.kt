package ie.wit.guitarApp.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.wit.guitarApp.models.GuitarAppModel
import ie.wit.guitarApp.models.GuitarStore
import timber.log.Timber


object FirebaseDBManager : GuitarStore {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(userid: String, guitarList: MutableLiveData<List<GuitarAppModel>>) {

        database.child("user-guitars").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Guitar error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<GuitarAppModel>()
                    val children = snapshot.children
                    children.forEach {
                        val guitar = it.getValue(GuitarAppModel::class.java)
                        localList.add(guitar!!)
                    }
                    database.child("user-guitars").child(userid)
                        .removeEventListener(this)

                    guitarList.value = localList
                }
            })
    }

    override fun findAll(guitarList: MutableLiveData<List<GuitarAppModel>>) {
        TODO("Not yet implemented")
    }

    override fun findById(userid: String, guitarid: String, guitar: MutableLiveData<GuitarAppModel>) {

        database.child("user-guitars").child(userid)
            .child(guitarid).get().addOnSuccessListener {
                guitar.value = it.getValue(GuitarAppModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener {
                Timber.e("firebase Error getting data $it")
            }
    }


    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, guitar: GuitarAppModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("guitars").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        guitar.uid = key
        // map created in the model
        val guitarValues = guitar.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/guitars/$key"] = guitarValues
        childAdd["/user-guitars/$uid/$key"] = guitarValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, guitarid: String) {

        val childDelete: MutableMap<String, Any?> = HashMap()
        childDelete["/guitars/$guitarid"] = null
        childDelete["/user-guitars/$userid/$guitarid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, guitarid: String, guitar: GuitarAppModel) {

        val guitarValues = guitar.toMap()

        val childUpdate: MutableMap<String, Any?> = HashMap()
        childUpdate["guitars/$guitarid"] = guitarValues
        childUpdate["user-guitars/$userid/$guitarid"] = guitarValues

        database.updateChildren(childUpdate)
    }
}
