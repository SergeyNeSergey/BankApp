package ru.nikanorovsa.bankapp.viewmodels

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import ru.nikanorovsa.bankapp.data.ListOfCards
import ru.nikanorovsa.bankapp.data.retrofit.CardsController
import ru.nikanorovsa.bankapp.data.room.CardsDao

class CardListFragmentViewModel @ViewModelInject constructor(
    //private val cardsDao: CardsDao,
    private val cardsController: CardsController) : ViewModel() {

   // val cards = cardsDao.getAll()

    fun initRateList() {
        val observable = cardsController.getCardsAsync()
        observable
            .subscribeOn(Schedulers.io())
            .toFlowable(BackpressureStrategy.BUFFER).observeOn(Schedulers.io())
            .subscribe(object :
                DisposableSubscriber<ListOfCards>() {
                override fun onComplete() {}
                override fun onNext(t: ListOfCards?) {
                    if (t != null) {
                      //  cardsDao.updateData(t.cards)
                    }
                }
                override fun onError(t: Throwable?) {
                    Log.d("eee", t.toString())
                }
            })
    }

}