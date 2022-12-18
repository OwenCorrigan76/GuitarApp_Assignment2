package ie.wit.guitarApp.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
// import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class GuitarAppModel(
    var uid: String = "",
    var guitarMake: String = "",
    var guitarModel: String = "",
    var value: Double = 0.0,
    var valuation: Double = 0.0,
    var manufactureDate: String = "",
    val serialNumber: Long = 0L,
    var message: String = "Click to edit",
    var image: String =  "",
    var profilepic: String = "",
    var email: String = "joe@bloggs.com",
    var lat : Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f

) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "valuation" to valuation,
            "guitarMake" to guitarMake,
            "message" to message,
            "guitarModel" to guitarModel,
            "manufactureDate" to manufactureDate,
            "email" to email,
            "image" to image,
            "profilepic" to profilepic,
            "lat" to lat,
            "lng" to lng,
            "zoom" to zoom
        )
    }
}
@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f) : Parcelable
