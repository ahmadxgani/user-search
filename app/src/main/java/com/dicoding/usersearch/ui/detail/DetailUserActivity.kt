package com.dicoding.usersearch.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.usersearch.R
import com.dicoding.usersearch.data.response.DetailUserResponse
import com.dicoding.usersearch.data.response.toUserItem
import com.dicoding.usersearch.databinding.ActivityDetailUserBinding
import com.dicoding.usersearch.ui.ViewModelFactory
import com.dicoding.usersearch.ui.detail.fragment.FollowerViewModel
import com.dicoding.usersearch.ui.detail.fragment.FollowingViewModel
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
        with (binding) {
        if (isLoading) {
            pbDetailUser.visibility = View.VISIBLE

            tvFollower.visibility = View.INVISIBLE
            tvFollowing.visibility = View.INVISIBLE
            tvGist.visibility = View.INVISIBLE
            tvName.visibility = View.INVISIBLE
            tvCreated.visibility = View.INVISIBLE
            tvRepo.visibility = View.INVISIBLE
        } else {
            pbDetailUser.visibility = View.GONE

            tvFollower.visibility = View.VISIBLE
            tvFollowing.visibility = View.VISIBLE
            tvGist.visibility = View.VISIBLE
            tvName.visibility = View.VISIBLE
            tvCreated.visibility = View.VISIBLE
            tvRepo.visibility = View.VISIBLE
        }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.extras!!.getString(USERNAME)!!

        val detailViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(application)
        )[DetailUserViewModel::class.java]

        val followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowingViewModel::class.java]

        val followerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowerViewModel::class.java]

        detailViewModel.getDetailUser(username)
        followerViewModel.getListFollower(username)
        followingViewModel.getListFollowing(username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, followingViewModel, followerViewModel)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.detailUser.observe(this) { fetchedUser ->
            detailViewModel.getUserByNode(fetchedUser.nodeId).observe(this) { storedUser ->
                val icon =
                    if (storedUser != null) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                binding.fabAddOrDelete.setImageResource(icon)
                binding.fabAddOrDelete.visibility = View.VISIBLE

                binding.fabAddOrDelete.setOnClickListener {
                    if (storedUser != null) detailViewModel.delete(storedUser)
                    else detailViewModel.insert(fetchedUser.toUserItem())
                }

            }
            setUser(fetchedUser)
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
        with(binding) {
            fabOpenLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://gist.github.com/${user.login}"))
                startActivity(intent)
            }

            tvUsername.text = user.login
            tvGist.text = getString(R.string.total_gists, user.publicGists)
            tvName.text = getString(R.string.full_name, user.name)
            tvFollower.text = getString(R.string.total_followers, user.followers)
            tvFollowing.text = getString(R.string.total_followings, user.following)
            tvRepo.text = getString(R.string.total_repositories, user.publicRepos)
            tvCreated.text = getString(R.string.account_created_at, user.createdAt)
            Glide.with(this@DetailUserActivity)
                .load(user.avatarUrl)
                .into(ivProfileDetail)
        }
    }
}