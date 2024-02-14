package com.dicoding.usersearch.data.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
data class UserItem(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	var id: Int = 0,

	@field:SerializedName("login")
	@ColumnInfo(name = "username")
	val login: String,

	@field:SerializedName("avatar_url")
	@ColumnInfo(name = "profile_url")
	val avatarUrl: String,

	@ColumnInfo(name = "node_id")
	val nodeId: String,
): Parcelable