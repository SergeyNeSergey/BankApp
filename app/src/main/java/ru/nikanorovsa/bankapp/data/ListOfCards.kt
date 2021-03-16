package ru.nikanorovsa.bankapp.data

import com.google.gson.annotations.SerializedName

data class ListOfCards (
    @SerializedName("cards") val cards : List<Card>
)