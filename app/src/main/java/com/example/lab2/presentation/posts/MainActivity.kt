package com.example.lab2.presentation.posts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab2.R
import com.example.lab2.data.network.api.NewsApi
import com.example.lab2.data.repositories.NewsDatabaseRepositoryImpl
import com.example.lab2.data.repositories.NewsNetworkRepositoryImpl
import com.example.lab2.databinding.ActivityMainBinding
import com.example.lab2.domain.interactors.NewsInteractor
import com.example.lab2.domain.interactors.NewsInteractorImpl
import com.sfsas.lab1.presentation.Lab2Application
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var adapter = PostsAdapter()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var interactor: NewsInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        interactor = NewsInteractorImpl(
            NewsDatabaseRepositoryImpl(
                (application as Lab2Application).database.newsDao(),
                (application as Lab2Application).database.newsTextDao()
            ),
            NewsNetworkRepositoryImpl(NewsApi())
        )
    }

    private fun setProgress(b: Boolean = true) {
        binding.swipeRefresh.isRefreshing = b
    }

    private fun load() {
        val disposable = interactor.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { posts ->
                adapter.setValue(posts)
            }

        compositeDisposable.add(disposable)
    }

    private fun refresh() {
        setProgress()

        val disposable = interactor.update()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { setProgress(false) }

        compositeDisposable.add(disposable)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener { refresh() }

        load()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
