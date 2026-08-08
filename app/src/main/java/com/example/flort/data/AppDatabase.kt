package com.example.flort.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Partner::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun partnerDao(): PartnerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "flort_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
