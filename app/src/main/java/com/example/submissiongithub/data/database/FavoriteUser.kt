package com.example.submissiongithub.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int? = null,

    @ColumnInfo(name = "username")
    var username : String? = null,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl : String? = null
): Parcelable