package com.example.lab2.domain.repositories

import com.example.lab2.data.db.entities.NewsDataModel
import com.example.lab2.data.db.entities.NewsTextDataModel
import com.example.lab2.domain.entities.News
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface NewsDatabaseRepository {
    fun getAll(): Flowable<List<NewsDataModel>>
    fun get(id: Int): Maybe<NewsDataModel>
    fun getText(id: Int) : Maybe<NewsTextDataModel>
    fun save(newsList : List<NewsDataModel>) :Completable
    fun save(newsText: NewsTextDataModel) : Completable
}