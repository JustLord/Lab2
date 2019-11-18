package com.example.lab2.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NewsText")
data class NewsTextDataModel(
    @PrimaryKey val id: Int,
    val text: String
)