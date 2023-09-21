package com.example.submissiongithub.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiongithub.data.database.FavoriteUser
import com.example.submissiongithub.data.repository.FavoriteRepository

class FavoriteViewModel(application : Application) : ViewModel(){
    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)

    fun getAllFavoriteUser() : LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavoriteUser()
}