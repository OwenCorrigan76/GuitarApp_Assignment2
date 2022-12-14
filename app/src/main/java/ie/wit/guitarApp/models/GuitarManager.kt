package ie.wit.guitarApp.models

import androidx.lifecycle.MutableLiveData
import ie.wit.guitarApp.api.GuitarClient
import ie.wit.guitarApp.api.GuitarWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


object GuitarManager : GuitarStore {

    private var guitars = ArrayList<GuitarModel>()

    override fun findAll(guitarList: MutableLiveData<List<GuitarModel>>) {

        val call = GuitarClient.getApi().findall()

        call.enqueue(object : Callback<List<GuitarModel>> {
            override fun onResponse(call: Call<List<GuitarModel>>,
                                    response: Response<List<GuitarModel>>
            ) {
                guitarList.value = response.body() as ArrayList<GuitarModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<GuitarModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findAll(email: String, guitarList: MutableLiveData<List<GuitarModel>>) {

        val call = GuitarClient.getApi().findall(email)

        call.enqueue(object : Callback<List<GuitarModel>> {
            override fun onResponse(call: Call<List<GuitarModel>>,
                                    response: Response<List<GuitarModel>>
            ) {
                guitarList.value = response.body() as ArrayList<GuitarModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<GuitarModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findById(email: String, id: String, guitar: MutableLiveData<GuitarModel>)   {

        val call = GuitarClient.getApi().get(email,id)

        call.enqueue(object : Callback<GuitarModel> {
            override fun onResponse(call: Call<GuitarModel>, response: Response<GuitarModel>) {
                guitar.value = response.body() as GuitarModel
                Timber.i("Retrofit findById() = ${response.body()}")
            }

            override fun onFailure(call: Call<GuitarModel>, t: Throwable) {
                Timber.i("Retrofit findById() Error : $t.message")
            }
        })
    }

    override fun create(guitar: GuitarModel) {

        val call = GuitarClient.getApi().post(guitar.email, guitar)

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

    override fun delete(email: String, id: String) {
        val call = GuitarClient.getApi().delete(email, id)

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

    override fun update(email: String,id: String, guitar: GuitarModel) {

        val call = GuitarClient.getApi().put(email,id,guitar)

        call.enqueue(object : Callback<GuitarWrapper> {
            override fun onResponse(call: Call<GuitarWrapper>,
                                    response: Response<GuitarWrapper>
            ) {
                val guitarWrapper = response.body()
                if (guitarWrapper != null) {
                    Timber.i("Retrofit Update ${guitarWrapper.message}")
                    Timber.i("Retrofit Update ${guitarWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<GuitarWrapper>, t: Throwable) {
                Timber.i("Retrofit Update Error : $t.message")
            }
        })
    }
}