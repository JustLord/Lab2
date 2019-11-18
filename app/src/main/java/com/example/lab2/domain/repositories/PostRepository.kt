package com.example.lab2.domain.repositories

import com.example.lab2.domain.entities.Post
import io.reactivex.Single

interface PostRepository {
    fun getAll(): Single<List<Post>>
}