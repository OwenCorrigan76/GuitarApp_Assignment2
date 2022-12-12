package ie.wit.guitarApp.models

import androidx.lifecycle.MutableLiveData

interface GuitarStore {
    fun findAll(guitarList: MutableLiveData<List<GuitarModel>>)
    fun findById(id: String) : GuitarModel?
    fun create(guitar: GuitarModel)
    fun delete(id: String)

}
