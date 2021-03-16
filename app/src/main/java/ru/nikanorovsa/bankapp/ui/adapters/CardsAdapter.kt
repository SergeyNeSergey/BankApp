package ru.nikanorovsa.bankapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_card.view.*
import ru.nikanorovsa.bankapp.R
import ru.nikanorovsa.bankapp.data.Card
import ru.nikanorovsa.bankapp.databinding.ViewCardBinding

class CardsAdapter(
    var clickListener: RecyclerOnItemClickListener
) :
    ListAdapter<Card, CardsAdapter.CardsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        return CardsViewHolder(ViewCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class CardsViewHolder(val binding: ViewCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (card: Card) {
            binding.apply {
                cardViewNumber.text = cardViewNumber.context.getString(R.string.cards_list_fragment_shadow_number_card).format(card.number.toString().subSequence(12,card.number.toString().length))
                cardViewCount.text = card.sum.toString()
            }
        }
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val card: Card = getItem(position)
        holder.bind(card)
        holder.binding.root.setOnClickListener { clickListener.onItemClick(card) }
    }

    class DiffCallback : DiffUtil.ItemCallback<Card>(){
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean  = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Card, newItem: Card) = oldItem == newItem

    }
}

interface RecyclerOnItemClickListener {
    fun onItemClick(card: Card)
}

