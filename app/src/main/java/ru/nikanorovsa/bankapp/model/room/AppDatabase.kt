package ru.nikanorovsa.bankapp.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.nikanorovsa.bankapp.model.Cards

// Абстрактный класс с объектом-компаньёном для создания базы данных Room. Объект компаньён используется
// в качестве синглтона.
@Database(entities = [Cards::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardsDao(): CardsDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "cards_model"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
