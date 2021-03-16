package ru.nikanorovsa.bankapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import ru.nikanorovsa.bankapp.data.Card

@Dao
interface CardsDao {

    @Query("SELECT * FROM cards_model")
    fun getAll(): Flowable<List<Card>>

    @Query("SELECT * FROM cards_model WHERE id LIKE :id")
    fun findById(id: String): Flowable<Card>

    @Insert
    fun insertAll(rateModelList: List<Card>)

    @Query("DELETE FROM cards_model")
    fun deleteAllTable()

    @Transaction
    fun updateData(rate: List<Card>) {
        deleteAllTable()
        insertAll(rate)
    }
}