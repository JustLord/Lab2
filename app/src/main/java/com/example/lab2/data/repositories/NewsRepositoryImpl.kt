package com.example.lab2.data.repositories

import com.example.lab2.data.db.datastore.NewsDao
import com.example.lab2.data.db.entities.NewsDataModel
import com.example.lab2.data.network.api.PostsApi
import com.example.lab2.data.network.entities.NewsResponse
import com.example.lab2.domain.entities.News
import com.example.lab2.domain.repositories.NewsRepository
import io.reactivex.Single

class NewsRepositoryImpl(private val api: PostsApi, private val db: NewsDao) : NewsRepository {

    private fun newsFromNewsResponse(response: NewsResponse) =
        News(response.id, response.title, response.subtitle, response.imgUrl)

    private fun newsFromNewsDataModel(dataModel: NewsDataModel) =
        News(dataModel.id, dataModel.title, dataModel.subtitle, dataModel.imgUrl)


    private fun newsDataModelFromNewsResponse(response: NewsResponse) =
        NewsDataModel(response.id, response.title, response.subtitle, response.imgUrl)

    override fun get(id: Int): Single<News> {

    }

    override fun getAll(forceUpdate: Boolean): Single<List<News>> {

        val observable = db.getAll()
            .toObservable()
            .map { dataModels ->
                if (forceUpdate || dataModels.isEmpty()) {
                    return@map api.getNewsList()
                        .map { responses ->
                            db.insert(responses.map { response ->
                                newsDataModelFromNewsResponse(
                                    response
                                )
                            })
                            return@map responses.map { response -> newsFromNewsResponse(response) }
                        }
                } else {
                    return@map dataModels.map { dataModel -> newsFromNewsDataModel((dataModel)) }
                }
            }

        return observable.first()
    }
}