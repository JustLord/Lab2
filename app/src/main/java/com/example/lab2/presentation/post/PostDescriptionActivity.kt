package com.example.lab2.presentation.post

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.lab2.R
import com.example.lab2.databinding.ActivityPostDescriptionBinding
import com.example.lab2.databinding.ActivityPostDescriptionBindingImpl
import kotlinx.android.synthetic.main.activity_post_description.*

class PostDescriptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityPostDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_description)
        setSupportActionBar(toolbar)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

    }
}
