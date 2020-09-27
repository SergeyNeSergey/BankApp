package ru.nikanorovsa.bankapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.android.synthetic.main.card_fragment.*
import ru.nikanorovsa.bankapp.model.Cards
import ru.nikanorovsa.bankapp.model.room.AppDatabase
import ru.nikanorovsa.bankapp.model.room.CardsDao

// Фрагмент показывающий полные данные для отдельной карточки
class CardFragment : Fragment() {
    //База данных
    private var dataBase: AppDatabase? = null

    //Интерфейс для запроса к базе данных
    private var userDao: CardsDao? = null

    // Объект компаньон инициализирующий фрагмент, в его поле записывается айди карточки, для доступа
// к объекту в базе данных
    companion object {
        lateinit var id1: String
        fun newInstance(id: String): CardFragment {
            val args = Bundle()
            id1 = id
            args.putString("key", id)

            val fragment = CardFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.card_fragment, container, false)
        dataBase = AppDatabase.getAppDataBase(activity!!.applicationContext)
        userDao = dataBase?.cardsDao()
        val observable = userDao!!.findById(id1)
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(object :
            DisposableSubscriber<Cards>() {
            override fun onComplete() {

            }

            override fun onNext(t: Cards?) {
                val num=t?.number.toString()
                number.text = (num.subSequence(0,4).toString() +" " +num.subSequence(4,8)+ " "+ num.subSequence(8,12) + " " +num.subSequence(12,num.length))
                sum.text = t?.sum.toString()
                typeOfCard.text = when (t?.type) {
                    0 -> "Дебетовая"
                    else -> "Кредитовая"
                }
                procent.text = t?.sum?.times(0.06 / 12).toString()


            }

            override fun onError(t: Throwable?) {


            }
        })



        return v
    }
}