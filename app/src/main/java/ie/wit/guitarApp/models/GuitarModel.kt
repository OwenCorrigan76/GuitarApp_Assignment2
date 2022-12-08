package ie.wit.guitarApp.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GuitarModel(var id: Long = 0,
                       var guitarMake: String = "",
                       var guitarModel: String = "",
                       var value: Double = 0.0,
                       var valuation: Double = 0.0,
                       var manufactureDate: String = "",
                       val serialNumber: Long = 0L,
                       var image: Uri = Uri.EMPTY,
                       val paymentmethod: String = "N/A",
                       val amount: Int = 0) : Parcelable