package com.example.lab2.data.repositories

import com.example.lab2.data.network.api.PostsApi
import com.example.lab2.domain.entities.Post
import com.example.lab2.domain.repositories.PostRepository
import io.reactivex.Single

class PostRepositoryImpl : PostRepository {
    override fun getAll(): Single<List<Post>> {
        val api = PostsApi()
        val observable = api.getPreviewList().map{it.map{ post -> Post(post.title, post.subtitle, post.imgUrl, post.postUrl)}}

        return Single.fromObservable(observable)
    }
}