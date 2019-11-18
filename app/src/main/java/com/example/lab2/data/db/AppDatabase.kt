package com.example.lab2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lab2.data.db.datastore.NewsDao
import com.example.lab2.data.db.datastore.NewsTextDao
import com.example.lab2.data.db.entities.NewsDataModel
import com.example.lab2.data.db.entities.NewsTextDataModel

@Database(entities = [NewsDataModel::class, NewsTextDataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun newsTextDao(): NewsTextDao
}