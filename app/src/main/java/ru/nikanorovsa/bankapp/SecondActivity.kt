package ru.nikanorovsa.bankapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

//Активность содержащая второй фрагмент.При завпуске получает интент содержащий айди банковской карты.
class SecondActivity : AppCompatActivity() {
    val KEY = "key"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        val idFromIntent = intent.getStringExtra(KEY)
        if (idFromIntent != null) {
            val cardFragment = CardFragment.newInstance(idFromIntent)
            val manager = supportFragmentManager
            val args = Bundle()
            args.putSerializable(KEY, idFromIntent)
            cardFragment.arguments = args
            manager.beginTransaction().add(R.id.fragment_conainer, cardFragment).commit()
        } else {
            Toast.makeText(applicationContext, "empty data id", Toast.LENGTH_LONG)
                .show()
        }
    }
}