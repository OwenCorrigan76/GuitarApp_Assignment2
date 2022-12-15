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
    var message: String = "n/a",
    var image: Uri = Uri.EMPTY,
    var profilepic: String = "",
    var email: String = "joe@bloggs.com"
) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "valuation" to valuation,
            "guitarMake" to guitarMake,
      //      "message" to message,
            "guitarModel" to guitarModel,
            "manufactureDate" to manufactureDate,
            "email" to email,
        //    "image" to image,
            "profilepic" to profilepic,
        )
    }
}
