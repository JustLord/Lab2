package com.example.lab2.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News")
data class NewsDataModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val subtitle: String,
    val imgUrl: String
)
