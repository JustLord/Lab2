package com.example.lab2.data.repositories

import com.example.lab2.data.db.datastore.NewsDao
import com.example.lab2.data.db.datastore.NewsTextDao
import com.example.lab2.data.db.entities.NewsDataModel
import com.example.lab2.data.db.entities.NewsTextDataModel
import com.example.lab2.domain.repositories.NewsDatabaseRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class NewsDatabaseRepositoryImpl(
    private val newsDao: NewsDao,
    private val newsTextDao: NewsTextDao
) :
    NewsDatabaseRepository {
    override fun save(newsText: NewsTextDataModel): Completable {
        return newsTextDao.insert(newsText)
    }

    override fun save(newsList: List<NewsDataModel>): Completable {
        return newsDao.insert(newsList)
    }

    override fun getText(id: Int): Flowable<NewsTextDataModel> {
        return newsTextDao.get(id)
    }

    override fun get(id: Int): Flowable<NewsDataModel> {
        return newsDao.get(id)
    }

    override fun getAll(): Flowable<List<NewsDataModel>> {
        return newsDao.getAll()
    }
}