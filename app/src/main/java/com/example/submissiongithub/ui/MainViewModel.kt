package com.example.submissiongithub.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiongithub.data.response.GithubResponse
import com.example.submissiongithub.data.response.ItemsItem
import com.example.submissiongithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _items = MutableLiveData<List<ItemsItem>>()
    val items : LiveData<List<ItemsItem>> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    var query : String = "a"

    companion object {
        const val TAG = "MainViewModel"
    }

    fun changeQuery(queries : String) {
        query = queries
        showUserData()
    }

    init {
        showUserData()
    }

    private fun showUserData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserList(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null) {
                    _items.value = response.body()!!.items
                } else {
                    Log.d(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message!!)
            }

        })
    }

}