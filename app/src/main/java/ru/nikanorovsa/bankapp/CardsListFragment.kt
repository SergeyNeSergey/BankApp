package ru.nikanorovsa.bankapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.android.synthetic.main.list_fragment.*
import ru.nikanorovsa.bankapp.model.Cards
import ru.nikanorovsa.bankapp.model.ListOfCards
import ru.nikanorovsa.bankapp.model.retrofit.RetrofitFactory.cardsController
import ru.nikanorovsa.bankapp.model.room.AppDatabase
import ru.nikanorovsa.bankapp.model.room.CardsDao

//  Фрагмент отображающий RecyclerView со списком карт.
class CardsListFragment : Fragment(), RecyclerOnItemClickListener {
    //Лист поставляемый в  Recycler. Обновляется в методе initRateList и сохраняется в баззе данных
    var listRate: List<Cards> = ArrayList()

    //База данных
    private var dataBase: AppDatabase? = null

    //Интерфейс для запроса к базе данных
    private var cardsDao: CardsDao? = null

    //Ключ для интента
    private val KEY = "key"


    //Объект компаньон инициализирующий фрагмент
    companion object {
        fun newInstance(): CardsListFragment {
            val fragment = CardsListFragment()
            return fragment
        }
    }

    // В данном методе производится проверка наличия данных в базе данных. Инициализируется база данных на измениния в
    //которой подписывается слушатель. Инициализируется класс Recycler. При первом запуске приложения
    // обязательно подключение интернета. При последующих возможна работа без интернета, Recycler будет
    //проинициализирован из базы данных предыдущим сохраненным значением списка.
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        val v = inflater.inflate(R.layout.list_fragment, container, false)

        dataBase = activity?.let { AppDatabase.getAppDataBase(it) }
        cardsDao = dataBase?.cardsDao()
        val observable = cardsDao?.getAll()
        observable?.observeOn(AndroidSchedulers.mainThread())?.subscribe(object :
            DisposableSubscriber<List<Cards>>() {
            override fun onComplete() {


                recycler.adapter!!.notifyDataSetChanged()

            }


            override fun onError(t: Throwable?) {

                activity?.runOnUiThread {
                    val toast = Toast.makeText(activity, "$t", Toast.LENGTH_LONG)
                    toast.show()
                }
            }

            override fun onNext(t: List<Cards>?) {
                if (t == null) {
                    activity?.runOnUiThread {
                        val toast = Toast.makeText(
                            activity,
                            "$t" + "object in database",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                } else {
                    listRate = t
                    Log.d("eee", t.size.toString())
                    if (listRate.size != 0) {
                        recycler.layoutManager = LinearLayoutManager(activity)
                        recycler.adapter = activity?.let {
                            Recycler(
                                listRate,
                                it, this@CardsListFragment

                            )
                        }
                        Log.d("eee", "fff")
                    } else {
                        Log.d("eee", "fff")
                        initRateList()
                    }
                }


            }

        })







        return v
    }


    override fun onResume() {
        progressBar.visibility = View.GONE
        super.onResume()


    }

    //Создаю меню
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inf = activity?.menuInflater
        inf!!.inflate(R.menu.list_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inf)

    }

    //Отрабатываю клик на меню, вызываю метод initRateList()
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update -> {
                initRateList()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Метод для заполнения базы данных и списка объектов в массив поставляемый в RecyclerView.
// При вызове метода запускается прогресс бар, на объект observable полученный из UserController и
// приведённый к Flowable подписывается слушатель.
    private fun initRateList() {
        Log.d("eee", "entr")
        progressBar.visibility = View.VISIBLE
        val observable = cardsController.getCardsAsync()
        observable
            .subscribeOn(Schedulers.io())
            .toFlowable(BackpressureStrategy.BUFFER).observeOn(Schedulers.io())
            .subscribe(object :
                DisposableSubscriber<ListOfCards>() {
                override fun onComplete() {


                }

                override fun onNext(t: ListOfCards?) {
                    if (t != null) {


                        cardsDao?.updateData(t.cards)
                        activity?.runOnUiThread {


                            progressBar.visibility = View.GONE
                        }


                    }


                }

                override fun onError(t: Throwable?) {
                    Log.d("eee", t.toString())
                    activity?.runOnUiThread {
                        progressBar.visibility = View.GONE
                        val toast =
                            Toast.makeText(activity, "$t", Toast.LENGTH_LONG)
                        toast.show()
                    }

                }

            })

    }

    // Метод интерфейса RecyclerOnItemClickListener в котором отрабатывается нажатие на карточку и запуск
// активности содержащей в себе фрагмент
    override fun onItemClick(item: Cards, position: Int) {
        Log.d("eee", "onClick")
        val intent = Intent(activity, SecondActivity::class.java)
        intent.putExtra(KEY, item.id.toString())
        startActivity(intent)


    }


}

