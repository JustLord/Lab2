package com.example.lab2.data.network

import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.lab2.data.db.datastore.NewsDao
import com.example.lab2.data.db.entities.NewsDataModel

@Database(entities = [NewsDataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}