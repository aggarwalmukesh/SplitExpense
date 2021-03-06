package com.interview.splitexpense.db

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.interview.splitexpense.ExpenseConstants
import com.interview.splitexpense.model.Expense
import com.interview.splitexpense.model.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseClient {

    private const val DB_NAME = "expense_db"
    private var appDatabase: AppDatabase? = null
    private var validUserMutableData: MutableLiveData<Users> = MutableLiveData()
    private var usersListMutableData: MutableLiveData<List<Users>> = MutableLiveData()
    private var expensesMutableData: MutableLiveData<List<Expense>> = MutableLiveData()

    private fun getDbInstance(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
        }
        return appDatabase as AppDatabase
    }

    private fun getDao(context: Context) = getDbInstance(context).expenseDao()

    fun getSharedPreferences(context: Context) =
        context.getSharedPreferences(ExpenseConstants.KEY_PREFERENCES, 0)

    fun updateLoggedInUser(context: Context, userName: String?) =
        getSharedPreferences(context).edit().putString(ExpenseConstants.KEY_PREF_USER, userName)
            .commit()

    fun logoutUser(context: Context) =
        getSharedPreferences(context).edit().clear().commit()

    fun getLoggedInUser(context: Context) =
        getSharedPreferences(context).getString(ExpenseConstants.KEY_PREF_USER, null)


    fun getAppUsers(context: Context) =
        CoroutineScope(Dispatchers.IO).launch {
            usersListMutableData.postValue(getDao(context).getAllUsers())
        }

    fun registerUsers(): MutableLiveData<List<Users>> = usersListMutableData

    fun registerForLogin(): MutableLiveData<Users> = validUserMutableData

    fun registerExpenses(): MutableLiveData<List<Expense>> = expensesMutableData

    fun addUsers(context: Context, users: Array<Users>) =
        CoroutineScope(Dispatchers.IO).launch {
            getDao(context).insertAllUsers(*users)
        }

    fun findValidUser(context: Context, email: String, password: String) =
        CoroutineScope(Dispatchers.IO).launch {
            validUserMutableData.postValue(getDao(context).fetchUser(email, password))
        }

    fun fetchExpenses(context: Context) =
        CoroutineScope(Dispatchers.IO).launch {
            expensesMutableData.postValue(getDao(context).fetchExpenses())
        }

    fun addExpense(context: Context, expense: Expense) =
        CoroutineScope(Dispatchers.IO).launch {
            getDao(context).insertExpense(expense)
        }
}