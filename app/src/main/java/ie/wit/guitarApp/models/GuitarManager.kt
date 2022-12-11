package ie.wit.guitarApp.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object GuitarManager : GuitarStore {

    val guitars = ArrayList<GuitarModel>()

    override fun findAll(): List<GuitarModel> {
        return guitars
    }

    override fun findById(id:Long) : GuitarModel? {
        val foundGuitar: GuitarModel? = guitars.find { it.id == id }
        return foundGuitar
    }

    override fun create(guitar: GuitarModel) {
        guitar.id = getId()
        guitars.add(guitar)
        logAll()
    }

    fun logAll() {
        Timber.v("** Guitars List **")
        guitars.forEach { Timber.v("Guitar List Fom Model ${it}") }
    }
}