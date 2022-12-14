package ie.wit.guitarApp.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//cloud based - retrieving from a web server
object GuitarClient {
    val serviceURL = "https://donationx-multi-web-server.onrender.com/"

    fun getApi() : GuitarService {

        val gson = GsonBuilder().create()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()

        val apiInterface = Retrofit.Builder()
            .baseUrl(serviceURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        return apiInterface.create(GuitarService::class.java)
    }
}