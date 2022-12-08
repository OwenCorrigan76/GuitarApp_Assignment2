package ie.wit.guitarApp.models

interface GuitarStore {
    fun findAll() : List<GuitarModel>
    fun findById(id: Long) : GuitarModel?
    fun create(donation: GuitarModel)
}