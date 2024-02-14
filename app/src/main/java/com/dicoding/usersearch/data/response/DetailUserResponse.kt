package com.dicoding.usersearch.data.response

import com.google.gson.annotations.SerializedName

fun DetailUserResponse.toUserItem(): UserItem {
	return UserItem(login = login, avatarUrl = avatarUrl, nodeId = nodeId)
}

data class DetailUserResponse(
	@field:SerializedName("node_id")
	val nodeId: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("public_gists")
	val publicGists: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,
)