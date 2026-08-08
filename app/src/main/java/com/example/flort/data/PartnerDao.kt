package com.example.flort.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PartnerDao {

    @Insert
    suspend fun ekle(partner: Partner)

    @Update
    suspend fun guncelle(partner: Partner)

    @Delete
    suspend fun sil(partner: Partner)

    @Query("SELECT * FROM partner_table ORDER BY id DESC")
    fun tumunuGetir(): Flow<List<Partner>>

    @Query("SELECT * FROM partner_table WHERE id = :id")
    suspend fun idIleGetir(id: Int): Partner?
}
