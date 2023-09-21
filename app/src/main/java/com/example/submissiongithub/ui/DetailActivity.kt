package com.example.submissiongithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissiongithub.R
import com.example.submissiongithub.data.database.FavoriteUser
import com.example.submissiongithub.data.response.DetailUserResponse
import com.example.submissiongithub.data.retrofit.ApiConfig
import com.example.submissiongithub.databinding.ActivityDetailBinding
import com.example.submissiongithub.helper.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        //KEY_VALUE = username
        const val KEY_VALUE = "key_value"
        const val TAG = "DetailActivity"

        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }

    private lateinit var binding : ActivityDetailBinding
    private lateinit var userId : String

    //Data Favorite
    private var indicator : Boolean? = null
    private var favoriteUser : FavoriteUser? = null
    private var favoriteUser2 : FavoriteUser? = null
    private var favoriteUsername : String? = null
    private var favoriteUrl : String? = null

    private val detailViewModel : DetailViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra(KEY_VALUE).toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, userId)
        val viewPager : ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs : TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.fab.setOnClickListener(this)

        showUserData()
    }

    private fun showUserData() {
        showLoading(true)
        val client = ApiConfig.getApiService().getUserById(userId)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                showLoading(false)
                if(response.isSuccessful && response.body() != null) {
                    setUserData(response.body()!!)
                } else {
                    Log.d(TAG, response.message()!!)
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
                Log.d(TAG, t.message!!)
            }

        })
    }

    private fun setUserData(data: DetailUserResponse) {
        favoriteUsername = data.login
        favoriteUrl = data.avatarUrl

        binding.tvName.text = data.login
        binding.tvUsername.text = data.login
        binding.following.text = "${data.following} Following"
        binding.follower.text = "${data.followers} Followers"
        Glide.with(this@DetailActivity)
            .load(data.avatarUrl)
            .into(binding.imageView)

        detailViewModel.getFavoriteUserByByUsername(data.login).observe(this) { FavoriteUser ->
            if(FavoriteUser != null) {
                binding.fab.setImageResource(R.drawable.baseline_favorite_24)
                indicator = true
                favoriteUser2 = FavoriteUser
            } else {
                binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
                indicator = false
            }
        }
    }

    private fun showLoading(isLoading : Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fab -> {
                Log.d("INDEX", indicator.toString())
                favoriteUser = FavoriteUser()
                favoriteUser!!.username = favoriteUsername
                favoriteUser!!.avatarUrl = favoriteUrl

                if(indicator == false) {
                    detailViewModel.insert(favoriteUser!!)
                    indicator = true
                } else {
                    detailViewModel.delete(favoriteUser2!!)
                    indicator = false
                }
            }
        }
    }
}