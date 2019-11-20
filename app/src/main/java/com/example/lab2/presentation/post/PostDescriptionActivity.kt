package com.example.lab2.presentation.post

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.lab2.R
import com.example.lab2.data.network.api.NewsApi
import com.example.lab2.data.repositories.NewsDatabaseRepositoryImpl
import com.example.lab2.data.repositories.NewsNetworkRepositoryImpl
import com.example.lab2.databinding.ActivityPostDescriptionBinding
import com.example.lab2.databinding.ActivityPostDescriptionBindingImpl
import com.example.lab2.domain.interactors.NewsInteractor
import com.example.lab2.domain.interactors.NewsInteractorImpl
import com.sfsas.lab1.presentation.Lab2Application
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_post_description.*

class PostDescriptionActivity : AppCompatActivity() {
    private lateinit var interactor: NewsInteractor
    private lateinit var binding: ActivityPostDescriptionBinding
    private val compositeDisposable = CompositeDisposable()

    var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_description)
        interactor = NewsInteractorImpl(
            NewsDatabaseRepositoryImpl(
                (application as Lab2Application).database.newsDao(),
                (application as Lab2Application).database.newsTextDao()
            ),
            NewsNetworkRepositoryImpl(NewsApi())
        )
        setSupportActionBar(toolbar)
    }

    fun updateText(){
        val disposable = interactor.update(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            }

        compositeDisposable.add(disposable)
    }

    fun load(){
        val disposable = interactor.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                title = it.title
                binding.text.text.text = it.text
                Glide.with(applicationContext).load(it.imgUrl).into(image)

                if(it.title.isEmpty())
                    updateText()

            }


        compositeDisposable.add(disposable)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        id = intent.getIntExtra("id", -1)

        load()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {
        fun startActivity(context: Context, id: Int) {
            val intent = Intent(context, PostDescriptionActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }
}
