package com.dicoding.usersearch.ui.detail.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.usersearch.data.response.UserItem
import com.dicoding.usersearch.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    companion object {
        private const val TAG = "FollowingViewModel"
    }

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private val _listFollowing = MutableLiveData<List<UserItem>>()
    val listFollowing: LiveData<List<UserItem>> = _listFollowing

    fun getListFollowing(user: String) {
        _isLoadingFollowing.value = true
        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowing.value = responseBody
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
                _isLoadingFollowing.value = false
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _isLoadingFollowing.value = false
            }
        })
    }
}