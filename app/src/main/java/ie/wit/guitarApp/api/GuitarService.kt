package ie.wit.guitarApp.api

import ie.wit.guitarApp.models.GuitarModel
import retrofit2.Call
import retrofit2.http.*

interface GuitarService {
        @GET("/donations")
        fun findall(): Call<List<GuitarModel>>

        @GET("/donations/{email}")
        fun findall(@Path("email") email: String?)
                : Call<List<GuitarModel>>

        @GET("/donations/{email}/{id}")
        fun get(@Path("email") email: String?,
                @Path("id") id: String): Call<GuitarModel>

        @DELETE("/donations/{email}/{id}")
        fun delete(@Path("email") email: String?,
                   @Path("id") id: String): Call<GuitarWrapper>

        @POST("/donations/{email}")
        fun post(@Path("email") email: String?,
                 @Body guitar: GuitarModel)
                : Call<GuitarWrapper>

        @PUT("/donations/{email}/{id}")
        fun put(@Path("email") email: String?,
                @Path("id") id: String,
                @Body guitar: GuitarModel
        ): Call<GuitarWrapper>
}
