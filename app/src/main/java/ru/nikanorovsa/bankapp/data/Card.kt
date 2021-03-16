package ru.nikanorovsa.bankapp.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


//Модель данных для вывода всех карточек

@Entity(tableName = "cards_model")
@Parcelize
data class Card (
    @PrimaryKey @SerializedName("id") val id : Int,
    @SerializedName("number") val number : Long,
    @SerializedName("type") val type : Int,
    @SerializedName("sum") val sum : Double
) : Parcelable