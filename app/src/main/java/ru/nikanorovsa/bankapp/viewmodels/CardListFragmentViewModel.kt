package ru.nikanorovsa.bankapp.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subscribers.DisposableSubscriber
import retrofit2.Retrofit
import ru.nikanorovsa.bankapp.data.ListOfCards
import ru.nikanorovsa.bankapp.data.retrofit.CardsController
import ru.nikanorovsa.bankapp.data.room.CardsDao

class CardListFragmentViewModel @ViewModelInject constructor(
    private val cardsDao: CardsDao,
    private val repository: CardsController
) : ViewModel() {

    val cards = cardsDao.getAll()
    var status: BehaviorSubject<Status> = BehaviorSubject.create()

    fun initRateList() {
        val observable = repository.getCardsAsync()
        observable
            .subscribeOn(Schedulers.io())
            .toFlowable(BackpressureStrategy.BUFFER).observeOn(Schedulers.io())
            .subscribe(object :
                DisposableSubscriber<ListOfCards>() {
                override fun onComplete() {
                    status.onNext(Status.SUCCESS)
                }
                override fun onNext(t: ListOfCards?) {
                    if (t != null) {
                        status.onNext(Status.LOADING)
                        cardsDao.updateData(t.cards)
                    }
                    status.onNext(Status.EMPTY)
                }
                override fun onError(t: Throwable?) {
                    val error = Status.ERROR
                    error.error = t?.message
                    status.onNext(error)
                }
            })
    }
}

enum class Status {
    SUCCESS {
        override var error: String? = null
            },
    ERROR {
        override var error: String? = null
    },
    LOADING {
        override var error: String? = null
    },
    EMPTY {
        override var error: String? = null
    };
    abstract var error: String?
}
