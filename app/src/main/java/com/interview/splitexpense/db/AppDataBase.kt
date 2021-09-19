package com.interview.splitexpense.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.interview.splitexpense.model.Users

@Database(entities = arrayOf(Users::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}