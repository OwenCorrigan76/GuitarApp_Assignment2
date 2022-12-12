package ie.wit.guitarApp.api

import ie.wit.guitarApp.models.GuitarModel
import retrofit2.Call
import retrofit2.http.*

interface GuitarService {
        @GET("/donations")
        fun getall(): Call<List<GuitarModel>>

        @GET("/donations/{id}")
        fun get(@Path("id") id: String): Call<GuitarModel>

        @DELETE("/donations/{id}")
        fun delete(@Path("id") id: String): Call<GuitarWrapper>

        @POST("/donations")
        fun post(@Body guitar: GuitarModel): Call<GuitarWrapper>

        @PUT("/donations/{id}")
        fun put(@Path("id") id: String,
                @Body guitar: GuitarModel
        ): Call<GuitarWrapper>
    }
