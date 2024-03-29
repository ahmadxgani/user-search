package com.dicoding.usersearch.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.usersearch.databinding.ActivityFavoriteListBinding
import com.dicoding.usersearch.ui.ViewModelFactory
import com.dicoding.usersearch.ui.UserAdapter

class FavoriteListActivity : AppCompatActivity() {
    private var _activityFavoriteBinding: ActivityFavoriteListBinding? = null
    private val binding get() = _activityFavoriteBinding!!
    private lateinit var adapter: UserAdapter
    private lateinit var favoriteListViewModel: FavoriteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteListActivity)
            rvFav.setHasFixedSize(true)
            adapter = UserAdapter()
            rvFav.adapter = adapter
        }

        favoriteListViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(application)
        )[FavoriteListViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        favoriteListViewModel.getAllUser().observe(this) { userList ->
            if (userList.isNotEmpty()) {
                binding.tvLabelFav.visibility = View.GONE
                binding.rvFav.visibility = View.VISIBLE
                adapter.submitList(userList)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
}