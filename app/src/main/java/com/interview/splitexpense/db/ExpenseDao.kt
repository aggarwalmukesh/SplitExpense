package com.interview.splitexpense.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.interview.splitexpense.model.Expense
import com.interview.splitexpense.model.Users

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<Users>

    @Query("SELECT * FROM users WHERE lower(email)= :userEmail OR lower(name)= :userEmail AND password= :pwd")
    fun fetchUser(userEmail: String, pwd: String): Users

    @Query("SELECT * FROM expense")
    fun fetchExpenses(): List<Expense>

    @Insert
    fun insertAllUsers(vararg users: Users)

    @Insert
    fun insertExpense(vararg expense: Expense)
}