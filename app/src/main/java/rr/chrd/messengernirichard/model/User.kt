package rr.chrd.messengernirichard.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var uid : String? = "",
    var username : String? ="",
    var email : String? = "",
    var profileImageUrl : String? = ""
) : Parcelable
