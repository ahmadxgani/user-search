package com.dicoding.usersearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.usersearch.databinding.ActivityFavoriteListBinding

class FavoriteListActivity : AppCompatActivity() {
    private var _activityFavoriteBinding: ActivityFavoriteListBinding? = null
    private val binding get() = _activityFavoriteBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}