package com.sfsas.lab1.presentation

import android.app.Application
import androidx.room.Room
import com.example.lab2.data.db.AppDatabase

class Lab2Application : Application() {

    lateinit var database : AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java,"database.db").build()
    }
}