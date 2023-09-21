package com.example.submissiongithub.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter (activity : AppCompatActivity, private val username : String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = Follow()
        fragment.arguments = Bundle().apply {
            putInt(Follow.SECTION_NUMBER, position + 1)
            putString(Follow.USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}