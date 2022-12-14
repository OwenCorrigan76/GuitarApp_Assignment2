package ie.wit.guitarApp.models

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class GuitarModel(
    var _id: String = "N/A",
    @SerializedName("paymenttype")
    var guitarMake: String = "",
   // @SerializedName("email")
    var guitarModel: String = "",
    var value: Double = 0.0,
   //  @SerializedName("amount")
    var valuation: Double = 0.0,
    var manufactureDate: String = "",
    val serialNumber: Long = 0L,
    var message: String = "n/a",
    var image: Uri = Uri.EMPTY,
    val paymentmethod: String = "N/A",
    val amount: Int = 0,
    var email: String = "joe@bloggs.com"
) : Parcelable
