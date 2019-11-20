package com.example.lab2.domain.interactors

import com.example.lab2.domain.entities.FullNews
import com.example.lab2.domain.entities.News
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

interface NewsInteractor {

    fun getAll(): Flowable<List<News>>
    fun get(id: Int): Maybe<FullNews>
    fun update(): Completable
    fun update(id: Int): Completable

}