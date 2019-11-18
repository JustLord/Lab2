package com.example.lab2.data.network.api

import com.example.lab2.data.network.entities.NewsTextResponse
import com.example.lab2.data.network.entities.NewsResponse
import io.reactivex.Observable
import io.reactivex.Single
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.regex.Pattern

class NewsApi {
    private fun connect(url: String): Single<Document> {
        return Single.fromCallable { Jsoup.connect(url).get() }
    }



    fun getNews(offset: Int = 0): Single<Document> {
        return connect("https://kg-portal.ru/news/anime/$offset/")
    }

    fun getNewsText(id: Int): Single<Document> {
        return connect("https://kg-portal.ru/comments/$id")
//            .map { it.select(".news_text").first() }
//            .map { it.select("p") }
//            .map { it.map { element -> element.text() } }
//            .map { NewsTextResponse(id, it.joinToString("\n")) }
    }
}