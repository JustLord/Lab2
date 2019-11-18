package com.example.lab2.data.db.datastore

import androidx.room.*
import com.example.lab2.data.db.entities.NewsTextDataModel
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface NewsTextDao {
    @Query("SELECT * FROM NewsText WHERE id = :id LIMIT 1")
    fun get(id: Int): Flowable<NewsTextDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsText: NewsTextDataModel): Completable

    @Delete
    fun delete(newsText: NewsTextDataModel): Completable
}

