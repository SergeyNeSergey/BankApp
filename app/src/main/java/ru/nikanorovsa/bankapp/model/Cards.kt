package ru.nikanorovsa.bankapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


//Модель данных для вывода всех карточек

@Entity(tableName = "cards_model")

data class Cards (

    @PrimaryKey @SerializedName("id") val id : Int,
    @SerializedName("number") val number : Long,
    @SerializedName("type") val type : Int,
    @SerializedName("sum") val sum : Double
)