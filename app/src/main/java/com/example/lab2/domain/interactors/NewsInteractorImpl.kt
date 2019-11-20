package com.example.lab2.domain.interactors

import com.example.lab2.data.db.entities.NewsDataModel
import com.example.lab2.data.db.entities.NewsTextDataModel
import com.example.lab2.data.network.entities.NewsResponse
import com.example.lab2.domain.entities.FullNews
import com.example.lab2.domain.entities.News
import com.example.lab2.domain.repositories.NewsDatabaseRepository
import com.example.lab2.domain.repositories.NewsNetworkRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.rxkotlin.zipWith

class NewsInteractorImpl(
    private val db: NewsDatabaseRepository,
    private val network: NewsNetworkRepository
) : NewsInteractor {

    private fun newsFromNewsDataModel(dataModel: NewsDataModel) =
        News(dataModel.id, dataModel.title, dataModel.subtitle, dataModel.imgUrl)


    private fun newsDataModelFromNewsResponse(response: NewsResponse) =
        NewsDataModel(response.id, response.title, response.subtitle, response.imgUrl)


    override fun getAll(): Flowable<List<News>> {
        return db.getAll().map { it.map { newsDataModel -> newsFromNewsDataModel(newsDataModel) } }
    }

    override fun get(id: Int): Maybe<FullNews> {
        val newsObservable = db.get(id)
        val newsTextObservable = db.getText(id)

        return newsObservable.zipWith(newsTextObservable) { a, b ->
            FullNews(
                a.id,
                a.title,
                b.text,
                a.imgUrl
            )
        }
    }

    override fun update(): Completable {
        return Completable.fromCallable {
            network.loadNews()
                .map { it.map { response -> newsDataModelFromNewsResponse(response) } }
                .subscribe { t1 -> db.save(t1).subscribe() }
        }
    }

    override fun update(id: Int): Completable {
        return Completable.fromCallable {
            network.loadNewsText(id).map { NewsTextDataModel(id, it) }
                .subscribe { t1 -> db.save(t1).subscribe() }
        }
    }

}