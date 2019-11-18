package com.example.lab2.data.repositories

import com.example.lab2.data.db.datastore.NewsDao
import com.example.lab2.data.db.entities.NewsDataModel
import com.example.lab2.data.network.api.PostsApi
import com.example.lab2.data.network.entities.NewsResponse
import com.example.lab2.domain.entities.News
import com.example.lab2.domain.repositories.NewsRepository
import io.reactivex.Observable
import io.reactivex.Single

class NewsRepositoryImpl(private val api: PostsApi, private val db: NewsDao) : NewsRepository {

    private fun newsFromNewsDataModel(dataModel: NewsDataModel) =
        News(dataModel.id, dataModel.title, dataModel.subtitle, dataModel.imgUrl)


    private fun newsDataModelFromNewsResponse(response: NewsResponse) =
        NewsDataModel(response.id, response.title, response.subtitle, response.imgUrl)

    override fun get(id: Int): Single<News> {
        val observable = db.get(id).map { newsFromNewsDataModel(it) }.toObservable()
        return Single.fromObservable(observable)
    }

    override fun getAll(forceUpdate: Boolean): Single<List<News>> {

        val observable = db.getAll()
            .toObservable()
            .flatMap {
                if (forceUpdate || it.isEmpty()) {
                    api.getNewsList().map { responses ->
                        val result =
                            responses.map { response -> newsDataModelFromNewsResponse(response) }
                        db.insert(result)
                        return@map result
                    }
                } else {
                    Observable.just(it)
                }
            }
            .map { it.map { dataModel -> newsFromNewsDataModel(dataModel) } }

        return Single.fromObservable(observable)
    }
}