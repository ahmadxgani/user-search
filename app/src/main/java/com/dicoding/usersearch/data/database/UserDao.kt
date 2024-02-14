package com.dicoding.usersearch.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.usersearch.data.response.UserItem

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserItem)

    @Delete
    fun delete(user: UserItem)

    @Query("SELECT * from users ORDER BY id ASC")
    fun getAllUser(): LiveData<List<UserItem>>

    @Query("SELECT * from users WHERE node_id = :nodeId")
    fun getUserByNode(nodeId: String): LiveData<UserItem>
}