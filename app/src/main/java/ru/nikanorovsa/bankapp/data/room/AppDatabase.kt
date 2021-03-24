package ru.nikanorovsa.bankapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nikanorovsa.bankapp.data.Card

@Database(entities = [Card::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardsDao(): CardsDao

}

