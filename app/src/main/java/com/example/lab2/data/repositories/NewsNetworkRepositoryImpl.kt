package com.example.lab2.data.repositories

import com.example.lab2.data.network.api.NewsApi
import com.example.lab2.data.network.entities.NewsResponse
import com.example.lab2.domain.repositories.NewsNetworkRepository
import io.reactivex.Single
import org.jsoup.nodes.Element
import java.util.regex.Pattern

class NewsNetworkRepositoryImpl(private val api: NewsApi) : NewsNetworkRepository {

    private fun getInt(str: String): Int {
        val p = Pattern.compile("-?\\d+")
        val mather = p.matcher(str)
        mather.find()
        return mather.group().toInt()
    }

    private fun makeNews(element: Element): NewsResponse {
        return NewsResponse(
            getInt(element.select("input").first().attr("value")),
            element.select("h2.news_title").text(),
            element.select(".lead").text(),
            "https://kg-portal.ru" + element.select("img").first().attr("src")
        )
    }

    override fun loadNews(): Single<List<NewsResponse>> {
        return api.getNews()
            .map { it.select(".news_box") }
            .map { it.map { element -> makeNews(element) } }
    }

    override fun loadNewsText(id: Int): Single<String> {
        return api.getNewsText(id)
            .map { it.select(".news_text").first() }
            .map { it.select("p") }
            .map { it.map { element -> element.text() } }
            .map { it.joinToString("\n") }
    }

}