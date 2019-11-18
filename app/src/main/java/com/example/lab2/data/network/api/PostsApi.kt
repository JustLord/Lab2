package com.example.lab2.data.network.api

import com.example.lab2.data.network.entities.NewsTextResponse
import com.example.lab2.data.network.entities.NewsResponse
import io.reactivex.Observable
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.regex.Pattern

class PostsApi {
    private fun connect(url: String): Observable<Document> {
        return Observable.fromCallable { Jsoup.connect(url).get() }
    }

    private fun getInt(str: String): Int {
        val p = Pattern.compile("-?\\d+")
        val mather = p.matcher(str)
        mather.find()
        return mather.group().toInt()
    }

    private fun makePreview(element: Element): NewsResponse {
        return NewsResponse(
            getInt(element.select("input").first().attr("value")),
            element.select("h2.news_title").text(),
            element.select(".lead").text(),
            "https://kg-portal.ru" + element.select("img").first().attr("src")
        )
    }

    fun getNewsList(offset: Int = 0): Observable<List<NewsResponse>> {
        return connect("https://kg-portal.ru/news/anime/$offset/")
            .map { it.select(".news_box") }
            .map { it.map { element -> makePreview(element) } }
    }

    fun getNewsText(id: Int): Observable<NewsTextResponse> {
        return connect("https://kg-portal.ru/comments/$id")
            .map { it.select(".news_text").first() }
            .map { it.select("p") }
            .map { it.map { element -> element.text() } }
            .map { NewsTextResponse(id, it.joinToString("\n")) }
    }
}