package space.lobanov.reqrestapplication.model

import com.google.gson.annotations.SerializedName

data class TokenData(
    @SerializedName("token")
    val token:String
)