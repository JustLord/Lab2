package com.example.lab2.data.db.datastore

import androidx.room.Dao

@Dao
interface NewsDao {
    @Query("SELECT * FROM Contact")
    fun getAll(): Flowable<List<ContactDataModel>>

    @Query("SELECT * FROM Contact WHERE id = :id LIMIT 1")
    fun get(id: Int) : Flowable<ContactDataModel>

    @Insert(onConflict = REPLACE)
    fun insert(contact: ContactDataModel) : Completable

    @Delete
    fun delete(contact: ContactDataModel) : Completable
}

