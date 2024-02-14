package com.dicoding.usersearch.ui.detail.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.usersearch.data.response.FollowResponseItem
import com.dicoding.usersearch.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {
    companion object {
        private const val TAG = "FollowerViewViewModel"
    }

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    private val _listFollower = MutableLiveData<List<FollowResponseItem>>()
    val listFollower: LiveData<List<FollowResponseItem>> = _listFollower

    fun getListFollower(user: String) {
        _isLoadingFollower.value = true
        val client = ApiConfig.getApiService().getFollower(user)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollower.value = responseBody
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
                _isLoadingFollower.value = false
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _isLoadingFollower.value = false
            }
        })
    }
}