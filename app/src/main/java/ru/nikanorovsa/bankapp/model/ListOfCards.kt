package ru.nikanorovsa.bankapp.model

import com.google.gson.annotations.SerializedName

//Класс POJO для получения данных из JSON через Retrofit

data class ListOfCards (

    @SerializedName("cards") val cards : List<Cards>
)