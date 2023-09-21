package com.example.submissiongithub.ui

import android.content.ClipData.Item
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submissiongithub.R
import com.example.submissiongithub.data.response.ItemsItem
import com.example.submissiongithub.data.retrofit.ApiConfig
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Follow : Fragment() {

    private var position = 0
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        position = arguments?.getInt(SECTION_NUMBER, 0)!!
        username = arguments?.getString(USERNAME).toString()

        val rvFollow : RecyclerView = view.findViewById(R.id.rv_Follow)

        val layoutManager = LinearLayoutManager(requireActivity())
        rvFollow.layoutManager = layoutManager

        val followViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]
        followViewModel.initializeData(username)

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if(position == 1) {
            followViewModel.following.observe(viewLifecycleOwner) {
                setFollowData(it)
            }
        } else {
            followViewModel.follower.observe(viewLifecycleOwner) {
                setFollowData(it)
            }
        }
    }

    private fun setFollowData(items: List<ItemsItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(items)
        view?.findViewById<RecyclerView>(R.id.rv_Follow)?.adapter = adapter
    }


    companion object {
        const val SECTION_NUMBER = "section_number"
        const val USERNAME = "username"
    }

    private fun showLoading(isLoading : Boolean) {
        if(isLoading) {
            view?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<ProgressBar>(R.id.progressBar)?.visibility = View.INVISIBLE
        }
    }
}