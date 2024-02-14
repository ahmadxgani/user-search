package com.dicoding.usersearch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.usersearch.data.response.UserItem

@Database(entities = [UserItem::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract  fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) : UserRoomDatabase {
            return INSTANCE ?: synchronized(UserRoomDatabase::class.java) {
                Room.databaseBuilder(context.applicationContext, UserRoomDatabase::class.java, "user_database").build()
            }.also { INSTANCE = it }
        }
    }
}