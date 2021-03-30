package ru.nikanorovsa.bankapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.android.synthetic.main.fragment_cards_list.*
import ru.nikanorovsa.bankapp.R
import ru.nikanorovsa.bankapp.data.Card
import ru.nikanorovsa.bankapp.databinding.FragmentCardsListBinding
import ru.nikanorovsa.bankapp.ui.adapters.CardsAdapter
import ru.nikanorovsa.bankapp.viewmodels.CardListFragmentViewModel
import ru.nikanorovsa.bankapp.viewmodels.Status

@AndroidEntryPoint
class CardsListFragment : Fragment(R.layout.fragment_cards_list) {

    private val viewModel : CardListFragmentViewModel by viewModels()

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val binding = FragmentCardsListBinding.bind(view)
        val cardsAdapter = CardsAdapter {
            val action = CardsListFragmentDirections.actionCardsListFragmentToCardFragment(it)
            findNavController().navigate(action)
        }
        binding.apply {
            cardsListFragmentRecycler.apply {
                adapter = cardsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.status.observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (it == Status.LOADING) {
                cardsListFragmentRecycler.visibility = GONE
                cardsListFragmentProgressBar.visibility = VISIBLE
            } else {
                cardsListFragmentProgressBar.visibility = GONE
                cardsListFragmentRecycler.visibility = VISIBLE
                if (it == Status.ERROR) {
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
        viewModel.cards.subscribe(object: DisposableSubscriber<List<Card>>() {
            override fun onNext(t: List<Card>?) {
                if (t.isNullOrEmpty()) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "$t" + "object in database", Toast.LENGTH_LONG).show()
                        viewModel.initRateList()
                    }
                } else {
                    cardsListFragmentProgressBar.visibility = GONE
                    cardsListFragmentRecycler.visibility = VISIBLE
                    cardsAdapter.submitList(t)
                }
            }
            override fun onError(t: Throwable?) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), t?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onComplete() {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update -> {
                viewModel.initRateList()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

