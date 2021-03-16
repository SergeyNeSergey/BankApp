package ru.nikanorovsa.bankapp.data.retrofit

import io.reactivex.Observable
import retrofit2.http.GET
import ru.nikanorovsa.bankapp.data.ListOfCards


interface CardsController {
    @GET("list.php")
    fun getCardsAsync(): Observable<ListOfCards>
}