package ru.nikanorovsa.bankapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// Стартовый экран содержащий в себе   Fragment с основной логикой
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userFragment = CardsListFragment.newInstance()
        val manager = supportFragmentManager
        manager.beginTransaction().add(R.id.fragment_conainer, userFragment).commit()

    }
}