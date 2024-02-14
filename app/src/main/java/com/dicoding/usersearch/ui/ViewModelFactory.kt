package com.dicoding.usersearch.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.usersearch.ui.detail.DetailUserViewModel
import com.dicoding.usersearch.ui.favorite.FavoriteListViewModel

class ViewModelFactory private constructor(private val mApplication: Application): ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance (application: Application) : ViewModelFactory {
            return INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                ViewModelFactory(application)
            }.also { INSTANCE = it }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteListViewModel::class.java)) {
            return FavoriteListViewModel(mApplication) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}