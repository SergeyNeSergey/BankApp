package ru.nikanorovsa.bankapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_fragment_holder.view.*
import ru.nikanorovsa.bankapp.model.Cards

// Стандартный класс для создания RecyclerView, единственная его особенность в том, что он дополнительно
//принимает var clickListener интерфейса RecyclerOnItemClickListener  для обработки нажатий и вывода
// выбранного Cards на отдельный экран.
class Recycler(
    val cardsList: List<Cards>,
    val context: Context,
    var clickListener: RecyclerOnItemClickListener

) :
    RecyclerView.Adapter<Recycler.ViewHolder>() {


    override fun getItemCount(): Int {
        return cardsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_fragment_holder,
                parent,
                false
            )
        )
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val number = view.number
        val count = view.count
        val center = view.textV
        val up =view.textUp
        val down= view.textDown

        fun init(item: Cards, action: RecyclerOnItemClickListener) {

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }


    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.center.setBackgroundColor(Color.BLUE)
        holder.up.setBackgroundColor(Color.BLACK)
        holder.down.setBackgroundColor(Color.BLACK)
        val cards: Cards = cardsList[position]
        holder.number.text ="XXXX XXXX XXXX " + cards.number.toString().subSequence(12,cards.number.toString().length)
        holder.count.text = cards.sum.toString()
        holder.init(cardsList.get(position), clickListener)

    }


}

// Интерфейс для отработки кликов на карточки
interface RecyclerOnItemClickListener {
    fun onItemClick(item: Cards, position: Int)
}

