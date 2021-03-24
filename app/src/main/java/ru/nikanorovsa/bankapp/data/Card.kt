package ru.nikanorovsa.bankapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cards_model")
@Parcelize
data class Card(
    @PrimaryKey val id: Int,
    val number: Long,
    val type: Int,
    val sum: Double
) : Parcelable