package com.dicoding.usersearch.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.usersearch.data.database.UserDao
import com.dicoding.usersearch.data.database.UserRoomDatabase
import com.dicoding.usersearch.data.response.UserItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<UserItem>> = mUserDao.getAllUser()

    fun getUserByNode(nodeId: String): LiveData<UserItem> = mUserDao.getUserByNode(nodeId)

    fun insert(user: UserItem) = executorService.execute { mUserDao.insert(user) }

    fun delete(user: UserItem) = executorService.execute { mUserDao.delete(user) }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        @JvmStatic
        fun getInstance(application: Application) : UserRepository {
            return INSTANCE ?: synchronized(this) {
                UserRepository(application)
            }.also { INSTANCE = it }
        }
    }
}