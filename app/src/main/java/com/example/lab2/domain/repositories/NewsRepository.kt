package com.example.lab2.domain.repositories

import com.example.lab2.domain.entities.News
import io.reactivex.Single

interface NewsRepository {
    fun getAll(forceUpdate: Boolean = false): Single<List<News>>
    fun get(id: Int): Single<News>
}