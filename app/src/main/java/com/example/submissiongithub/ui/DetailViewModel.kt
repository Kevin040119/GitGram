package com.example.submissiongithub.ui


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiongithub.data.database.FavoriteUser
import com.example.submissiongithub.data.repository.FavoriteRepository

class DetailViewModel(application : Application) : ViewModel() {
    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)

    fun insert(favoriteUser: FavoriteUser)  {
        mFavoriteRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteRepository.delete(favoriteUser)
    }

    fun getFavoriteUserByByUsername(username : String) : LiveData<FavoriteUser> = mFavoriteRepository.getFavoriteUserByUsername(username)

}