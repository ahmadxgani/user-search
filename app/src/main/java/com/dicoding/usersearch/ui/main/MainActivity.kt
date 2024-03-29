package com.dicoding.usersearch.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.usersearch.R
import com.dicoding.usersearch.data.response.UserItem
import com.dicoding.usersearch.databinding.ActivityMainBinding
import com.dicoding.usersearch.ui.UserAdapter
import com.dicoding.usersearch.ui.favorite.FavoriteListActivity
import com.dicoding.usersearch.ui.setting.SettingActivity
import com.dicoding.usersearch.ui.setting.SettingPreferences
import com.dicoding.usersearch.ui.setting.SettingViewModel
import com.dicoding.usersearch.ui.setting.SettingViewModelFactory
import com.dicoding.usersearch.ui.setting.dataStore

class MainActivity : AppCompatActivity() {
    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        with(binding) {
            sbUser.inflateMenu(R.menu.option_menu)
            sbUser.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu1 -> {
                        val intent = Intent(this@MainActivity, FavoriteListActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.menu2 -> {
                        val intent = Intent(this@MainActivity, SettingActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    else -> false
                }
            }

            searchView.setupWithSearchBar(sbUser)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchView.hide()
                mainViewModel.searchByName(searchView.text.toString())
                false
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        mainViewModel.listUser.observe(this) {
            setUserData(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbUserList.visibility = View.VISIBLE
        } else {
            binding.pbUserList.visibility = View.GONE
        }
    }

    private fun setUserData(users: List<UserItem>) {
        val adapter = UserAdapter()
        adapter.submitList(users)

        binding.rvUser.adapter = adapter
    }
}