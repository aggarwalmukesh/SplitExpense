package com.interview.splitexpense.db

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.interview.splitexpense.model.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseClient {

    private const val DB_NAME = "expense_db"
    private var appDatabase: AppDatabase? = null
    private var isValidUserMutableData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private var usersListMutableData: MutableLiveData<List<Users>> = MutableLiveData<List<Users>>()
    private fun getDbInstance(context: Context) : AppDatabase {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
        }
        return appDatabase as AppDatabase
    }

    private fun getDao(context: Context) = getDbInstance(context).expenseDao()

    fun getAppUsers(context: Context){
        CoroutineScope(Dispatchers.IO).launch {
            usersListMutableData.postValue(getDao(context).getAllUsers())
        }
    }

    fun registerUsers(): MutableLiveData<List<Users>> = usersListMutableData

    fun registerForLogin(): MutableLiveData<Boolean> = isValidUserMutableData

    fun addUsers(context: Context, users: Users) {
        CoroutineScope(Dispatchers.IO).launch {
            getDao(context).insertAll(users)
        }
    }

    fun isValidUser(context: Context, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            getDao(context).fetchUser(email, password)
        }
    }
}