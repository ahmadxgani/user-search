package com.dicoding.usersearch.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.usersearch.data.repository.UserRepository
import com.dicoding.usersearch.data.response.UserItem

class FavoriteListViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getAllUser(): LiveData<List<UserItem>> = userRepository.getAllUsers()
}