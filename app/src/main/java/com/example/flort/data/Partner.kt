package com.example.flort.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partner_table")
data class Partner(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val isim: String,
    val yas: Int,
    val sevdigiOzellikler: String,
    val hatirlanacakSeyler: String,
    val fotoUri: String? = null
)
