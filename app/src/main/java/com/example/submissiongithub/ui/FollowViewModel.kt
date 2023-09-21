package com.example.submissiongithub.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiongithub.data.response.ItemsItem
import com.example.submissiongithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following : LiveData<List<ItemsItem>> = _following

    private val _follower = MutableLiveData<List<ItemsItem>>()
    val follower : LiveData<List<ItemsItem>> = _follower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private var username : String = ""

    companion object {
        const val TAG = "FollowViewModel"
    }

    fun initializeData(usernames: String) {
        username = usernames
        Log.d("FollowVideModel",username)
        showFollowingData()
        showFollowerData()
    }

    private fun showFollowingData() {
        _isLoading.value = true
        Log.d("FollowVideModel2",username)

        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null) {
                    _following.value = response.body()
                } else {
                    Log.d(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, t.message!!)
            }

        })
    }

    private fun showFollowerData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null) {
                    _follower.value = response.body()
                } else {
                    Log.d(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, t.message!!)
            }

        })
    }
}