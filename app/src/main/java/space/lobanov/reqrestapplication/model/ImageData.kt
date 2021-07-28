package space.lobanov.reqrestapplication.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ImageData (
    @SerializedName("total")
    val totalData: String,
    @SerializedName("data")
    var listOfImage: List<Images>
)

@Entity(tableName = "image_items")
@Parcelize
class Images(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "image")
    @SerializedName("avatar")
    val userImageUrl: String,

    @ColumnInfo(name = "userEmail")
    @SerializedName("email")
    val userEmail:String,

    @ColumnInfo(name = "userName")
    @SerializedName("first_name")
    val userName:String
) :Parcelable

