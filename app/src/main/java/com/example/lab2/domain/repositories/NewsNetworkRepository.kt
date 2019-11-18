package com.example.lab2.domain.repositories

import com.example.lab2.data.network.entities.NewsResponse
import io.reactivex.Single

interface NewsNetworkRepository {
    fun loadNews(): Single<List<NewsResponse>>
    fun loadNewsText(id : Int) : Single<String>
}