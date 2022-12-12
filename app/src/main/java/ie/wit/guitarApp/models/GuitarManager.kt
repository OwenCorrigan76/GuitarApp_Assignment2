package ie.wit.guitarApp.models

import androidx.lifecycle.MutableLiveData
import ie.wit.guitarApp.api.GuitarClient
import ie.wit.guitarApp.api.GuitarWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object GuitarManager : GuitarStore {

  private var guitars = ArrayList<GuitarModel>()

    override fun findAll(guitarList: MutableLiveData<List<GuitarModel>>) {
        val call = GuitarClient.getApi().getall()

        call.enqueue(object : Callback<List<GuitarModel>> {
            override fun onResponse(call: Call<List<GuitarModel>>,
                                    response: Response<List<GuitarModel>>
            ) {
                guitarList.value = response.body() as ArrayList<GuitarModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<GuitarModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun findById(id:String) : GuitarModel? {
        val foundGuitar: GuitarModel? = guitars.find { it._id == id }
        return foundGuitar
    }

    override fun create(guitar: GuitarModel) {

        val call = GuitarClient.getApi().post(guitar)

        call.enqueue(object : Callback<GuitarWrapper> {
            override fun onResponse(call: Call<GuitarWrapper>,
                                    response: Response<GuitarWrapper>
            ) {
                val guitarWrapper = response.body()
                if (guitarWrapper != null) {
                    Timber.i("Retrofit ${guitarWrapper.message}")
                    Timber.i("Retrofit ${guitarWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<GuitarWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun delete(id: String) {
        val call = GuitarClient.getApi().delete(id)

        call.enqueue(object : Callback<GuitarWrapper> {
            override fun onResponse(call: Call<GuitarWrapper>,
                                    response: Response<GuitarWrapper>
            ) {
                val guitarWrapper = response.body()
                if (guitarWrapper != null) {
                    Timber.i("Retrofit Delete ${guitarWrapper.message}")
                    Timber.i("Retrofit Delete ${guitarWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<GuitarWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }

    fun logAll() {
        Timber.v("** Guitars List **")
        guitars.forEach { Timber.v("Guitar List Fom Model ${it}") }
    }
}