package com.example.lab2.data.db.datastore

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.lab2.data.db.entities.NewsDataModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface NewsDao {
    @Query("SELECT * FROM News")
    fun getAll(): Flowable<List<NewsDataModel>>

    @Query("SELECT * FROM News WHERE id = :id LIMIT 1")
    fun get(id: Int): Maybe<NewsDataModel>

    @Insert(onConflict = REPLACE)
    fun insert(newsList: List<NewsDataModel>): Completable

    @Insert(onConflict = REPLACE)
    fun insert(news: NewsDataModel): Completable

    @Delete
    fun delete(news: NewsDataModel): Completable
}

