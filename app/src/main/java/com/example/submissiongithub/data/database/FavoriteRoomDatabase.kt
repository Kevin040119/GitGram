package com.example.submissiongithub.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 2)
abstract class FavoriteRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao() : FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE : FavoriteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context : Context) : FavoriteRoomDatabase {
            if(INSTANCE == null) {
                synchronized(FavoriteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteRoomDatabase::class.java, "favorite_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavoriteRoomDatabase
        }
    }
}