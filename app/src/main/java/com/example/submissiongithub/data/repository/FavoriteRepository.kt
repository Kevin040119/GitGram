package com.example.submissiongithub.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.submissiongithub.data.database.FavoriteDao
import com.example.submissiongithub.data.database.FavoriteRoomDatabase
import com.example.submissiongithub.data.database.FavoriteUser
import com.example.submissiongithub.ui.Favorite
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao : FavoriteDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavoriteUser() : LiveData<List<FavoriteUser>> = mFavoriteDao.getAllFavoriteUser()

    fun getFavoriteUserByUsername(username : String) : LiveData<FavoriteUser> = mFavoriteDao.getFavoriteUserByUsername(username)

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute {mFavoriteDao.insert(favoriteUser)}
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute {mFavoriteDao.delete(favoriteUser)}
    }

}