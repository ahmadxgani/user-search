package com.dicoding.usersearch

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(
    activity: AppCompatActivity,
    private val followingViewModel: FollowingViewModel,
    private val followerViewModel: FollowerViewModel
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment(followingViewModel)

            1 -> fragment = FollowerFragment(followerViewModel)
        }

        return fragment as Fragment
    }
}