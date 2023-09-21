package com.example.submissiongithub.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.submissiongithub.R
import com.example.submissiongithub.data.response.GithubResponse
import com.example.submissiongithub.data.response.ItemsItem
import com.example.submissiongithub.data.retrofit.ApiConfig
import com.example.submissiongithub.databinding.ActivityMainBinding
import com.example.submissiongithub.theme.SettingPreferences
import com.example.submissiongithub.theme.ThemeActivity
import com.example.submissiongithub.theme.ThemeViewModel
import com.example.submissiongithub.theme.ThemeViewModelFactory
import com.example.submissiongithub.theme.dataStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var query : String = "q"

    private lateinit var binding : ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    query = textView.text.toString()
                    mainViewModel.changeQuery(query)
                    false
                }

            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {

                    R.id.favorites -> {
                        val intent = Intent(this@MainActivity,Favorite::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.action_setting -> {
                        val intent2 = Intent(this@MainActivity, ThemeActivity::class.java)
                        startActivity(intent2)
                        true
                    }
                }

                true
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.items.observe(this) {
            setUserData(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        //Theme
        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )

        themeViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    private fun setUserData(item : List<ItemsItem> ) {
        val adapter = UserAdapter()
        adapter.submitList(item)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading : Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}