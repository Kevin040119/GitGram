package com.example.submissiongithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submissiongithub.R
import com.example.submissiongithub.data.database.FavoriteUser
import com.example.submissiongithub.data.response.ItemsItem
import com.example.submissiongithub.databinding.ActivityFavoriteBinding
import com.example.submissiongithub.helper.ViewModelFactory

class Favorite : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding

    private val favoriteViewModel : FavoriteViewModel by viewModels() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = linearLayoutManager

        favoriteViewModel.getAllFavoriteUser().observe(this) { list ->
            setFavoriteData(list)
        }
    }

    private fun setFavoriteData(items: List<FavoriteUser>) {
        val adapter = FavoriteAdapter()
        adapter.submitList(items)
        binding.rvFavorite.adapter = adapter
    }

}