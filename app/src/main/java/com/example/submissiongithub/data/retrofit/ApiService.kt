package com.example.submissiongithub.data.retrofit

import android.telecom.Call
import com.example.submissiongithub.data.response.DetailUserResponse
import com.example.submissiongithub.data.response.GithubResponse
import com.example.submissiongithub.data.response.ItemsItem
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path


interface ApiService {
    @GET("search/users")
    fun getUserList(
        @Query("q") query : String
    ) : retrofit2.Call<GithubResponse>

    @GET("users/{username}")
    fun getUserById(
        @Path("username") username : String
    ) : retrofit2.Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username") username : String
    ) : retrofit2.Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username : String
    ) : retrofit2.Call<List<ItemsItem>>
}