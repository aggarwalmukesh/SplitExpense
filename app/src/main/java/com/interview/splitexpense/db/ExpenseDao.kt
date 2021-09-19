package com.interview.splitexpense.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.interview.splitexpense.model.Users

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<Users>

    @Query("SELECT * FROM users")
    fun fetchUser(email: String, password: String): Users

    @Insert
    fun insertAll(vararg users: Users)
}