package com.dicoding.usersearch.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.usersearch.data.repository.UserRepository
import com.dicoding.usersearch.data.response.DetailUserResponse
import com.dicoding.usersearch.data.response.UserItem
import com.dicoding.usersearch.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(private val userRepository: UserRepository): ViewModel() {
    companion object {
        private const val TAG = "DetailUserViewModel"
    }

    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    fun getDetailUser(user: String) {
        _isLoadingDetail.value = true
        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = responseBody
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
                _isLoadingDetail.value = false
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _isLoadingDetail.value = false
            }
        })
    }

    fun getUserByNode(nodeId: String) = userRepository.getUserByNode(nodeId)

    fun insert(user: UserItem) {
        userRepository.insert(user)
    }

    fun delete(user: UserItem) {
        userRepository.delete(user)
    }
}