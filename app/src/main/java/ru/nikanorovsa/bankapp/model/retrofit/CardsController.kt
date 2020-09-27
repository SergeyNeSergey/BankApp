package ru.nikanorovsa.bankapp.model.retrofit

import io.reactivex.Observable
import retrofit2.http.GET
import ru.nikanorovsa.bankapp.model.Cards
import ru.nikanorovsa.bankapp.model.ListOfCards


interface CardsController {
    /**
     * Метод для получения данных с сайта
     */
    @GET("list.php")
    fun getCardsAsync(): Observable<ListOfCards>
}