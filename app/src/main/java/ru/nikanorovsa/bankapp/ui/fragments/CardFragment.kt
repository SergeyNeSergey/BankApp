package ru.nikanorovsa.bankapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.nikanorovsa.bankapp.R
import ru.nikanorovsa.bankapp.databinding.FragmentCardBinding

class CardFragment : Fragment(R.layout.fragment_card) {

    val args: CardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val number = args.card.number.toString()
        val binding = FragmentCardBinding.bind(view)
        binding.apply {
            cardFragmentNumber.text = getString(R.string.card_fragment_number_of_card).format(
                number.subSequence(0, 4).toString(),
                number.subSequence(4, 8).toString(),
                number.subSequence(8, 12).toString(),
                number.subSequence(12, number.length)
            )
            cardFragmentSum.text = args.card.sum.toString()
            cardFragmentTypeOfCard.text = when (args.card.type) {
                0 -> getString(R.string.card_fragment_debit_card)
                else -> getString(R.string.card_fragment_credit_card)
            }
            cardFragmentPercent.text = args.card.sum.times(0.06 / 12).toString()
        }
    }
}