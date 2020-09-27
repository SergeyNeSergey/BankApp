package ru.nikanorovsa.bankapp.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import ru.nikanorovsa.bankapp.model.Cards

// Интерфейс для обращения к базе данных Room
@Dao
interface CardsDao {

    @Query("SELECT * FROM cards_model")
    fun getAll(): Flowable<List<Cards>>

    @Query("SELECT * FROM cards_model WHERE id LIKE :id")
    fun findById(id: String): Flowable<Cards>

    @Insert
    fun insertAll(rateModelList: List<Cards>)


    @Query("DELETE FROM cards_model")
    fun deleteAllTable()


    @Transaction
    fun updateData(rate: List<Cards>) {
        deleteAllTable()
        insertAll(rate)

    }
}