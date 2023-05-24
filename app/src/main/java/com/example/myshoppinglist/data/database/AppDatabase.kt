package com.example.myshoppinglist.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shopListDao(): ShopListDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val NAME_DB = "shopItems.db"

        fun getInstance(application: Application): AppDatabase {

            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val database = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    NAME_DB
                ).allowMainThreadQueries().build() // для теста чтобы можно было взаимодействовать с баззой данных в главном потоке
                INSTANCE = database
                return database
            }
        }
    }
}