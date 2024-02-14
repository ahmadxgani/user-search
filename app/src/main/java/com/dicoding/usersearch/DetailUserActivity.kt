package com.dicoding.usersearch

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.usersearch.data.response.DetailUserResponse
import com.dicoding.usersearch.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private var _activityDetailBinding: ActivityDetailUserBinding? = null
    private val binding get() = _activityDetailBinding!!

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        var USERNAME = ""
    }

    private fun showLoadingDetail(isLoading: Boolean) {
        if (isLoading) {
            binding.pbDetailUser.visibility = View.VISIBLE

            binding.tvFollower.visibility = View.INVISIBLE
            binding.tvFollowing.visibility = View.INVISIBLE
            binding.tvGist.visibility = View.INVISIBLE
            binding.tvName.visibility = View.INVISIBLE
            binding.tvCreated.visibility = View.INVISIBLE
            binding.tvRepo.visibility = View.INVISIBLE
        } else {
            binding.pbDetailUser.visibility = View.GONE

            binding.tvFollower.visibility = View.VISIBLE
            binding.tvFollowing.visibility = View.VISIBLE
            binding.tvGist.visibility = View.VISIBLE
            binding.tvName.visibility = View.VISIBLE
            binding.tvCreated.visibility = View.VISIBLE
            binding.tvRepo.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.extras!!.getString(USERNAME)!!

        val detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)

        val followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        val followerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)

        detailViewModel.getDetailUser(username)
        followerViewModel.getListFollower(username)
        followingViewModel.getListFollowing(username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, followingViewModel, followerViewModel)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.detailUser.observe(this) {
            setUser(it)
        }

        detailViewModel.isLoadingDetail.observe(this) {
            showLoadingDetail(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }

    private fun setUser(user: DetailUserResponse) {
        binding.tvUsername.text = user.login
        binding.tvGist.text = getString(R.string.total_gists, user.publicGists)
        binding.tvName.text = getString(R.string.full_name, user.name)
        binding.tvFollower.text = getString(R.string.total_followers, user.followers)
        binding.tvFollowing.text = getString(R.string.total_followings, user.following)
        binding.tvRepo.text = getString(R.string.total_repositories, user.publicRepos)
        binding.tvCreated.text = getString(R.string.account_created_at, user.createdAt)
        Glide.with(this)
            .load(user.avatarUrl)
            .into(binding.ivProfileDetail)
    }
}