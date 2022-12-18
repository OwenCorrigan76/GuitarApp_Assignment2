package ie.wit.guitarApp.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.wit.guitarApp.models.GuitarAppModel
import ie.wit.guitarApp.models.GuitarStore
import timber.log.Timber


object FirebaseDBManager : GuitarStore {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    // for single users - via userid
    override fun findAll(userid: String, guitarList: MutableLiveData<List<GuitarAppModel>>) {

        database.child("user-guitars").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Guitar error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<GuitarAppModel>()
      //get the .children property of the snapshot called children (collection)
                    val children = snapshot.children
    // for loop, running through each element, retrieving the data....
                    children.forEach {
                  // storing it it in tha guitar object and....
                        val guitar = it.getValue(GuitarAppModel::class.java)
              // adding it to localList as an ArrayList
                        localList.add(guitar!!)
                    }
                    database.child("user-guitars").child(userid)
                        .removeEventListener(this)

                    guitarList.value = localList
                }
            })
    }
    // for all users
    override fun findAll(guitarList: MutableLiveData<List<GuitarAppModel>>) {
        database.child("guitars")
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
                    database.child("guitars")
                        .removeEventListener(this)

                    guitarList.value = localList
                }
            })
    }

   /* override fun findAll(): List<GuitarAppModel> {
        TODO("Not yet implemented")
    }*/

    override fun findById(
        userid: String,
        guitarid: String,
        guitar: MutableLiveData<GuitarAppModel>
    ) {
            // retrieves dat from the user, dased on userid
        database.child("user-guitars").child(userid)
                // add for once off retrieval of the object
            .child(guitarid).get().addOnSuccessListener {
                // assign the retrieval to the value property of guitar
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



    override fun update(userid: String, guitarid: String, guitar: GuitarAppModel) {
   // creating a map with all of the data and passing to guitarValues
        val guitarValues = guitar.toMap()

        val childUpdate: MutableMap<String, Any?> = HashMap()
        // overwriting existing values for both updates
        childUpdate["guitars/$guitarid"] = guitarValues
        childUpdate["user-guitars/$userid/$guitarid"] = guitarValues
     // call it here and force update the changes
        database.updateChildren(childUpdate)
    }

    override fun delete(userid: String, guitarid: String) {
       // map through
        val childDelete: MutableMap<String, Any?> = HashMap()
        // delete by assigning the value to null
        childDelete["/guitars/$guitarid"] = null
        childDelete["/user-guitars/$userid/$guitarid"] = null

        database.updateChildren(childDelete)
    }

  /*  fun updateImageRef(userid: String, imageUri: String) {

        val userGuitars = database.child("user-guitars").child(userid)
        val allGuitars = database.child("guitars")

        userGuitars.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        //Update Users imageUri
                        it.ref.child("profilepic").setValue(imageUri)
                        //Update all donations that match 'it'
                        val guitar = it.getValue(GuitarAppModel::class.java)
                        allGuitars.child(guitar!!.uid!!)
                            .child("profilepic").setValue(imageUri)
                    }
                }
            })
    }*/
}
