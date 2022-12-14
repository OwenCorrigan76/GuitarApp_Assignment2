package ie.wit.guitarApp.models

import androidx.lifecycle.MutableLiveData

interface GuitarStore {
    fun findAll(guitarList: MutableLiveData<List<GuitarModel>>)
    fun findAll(email:String, guitarList: MutableLiveData<List<GuitarModel>>)
    fun findById(email:String, id: String, guitar: MutableLiveData<GuitarModel>)
    fun create(guitar: GuitarModel)
    fun delete(email : String, id: String)
    fun update(email: String,id: String, guitar: GuitarModel)
}
