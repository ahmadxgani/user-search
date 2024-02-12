package com.dicoding.usersearch.data.retrofit

import com.dicoding.usersearch.data.response.DetailUserResponse
import com.dicoding.usersearch.data.response.FollowResponseItem
import com.dicoding.usersearch.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsers(@Query("q") username: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollower(@Path("username") username: String): Call<List<FollowResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowResponseItem>>

}