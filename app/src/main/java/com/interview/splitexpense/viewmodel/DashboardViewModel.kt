package com.interview.splitexpense.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interview.splitexpense.db.DatabaseClient
import com.interview.splitexpense.model.Expense
import com.interview.splitexpense.model.Users

class DashboardViewModel(val application: Application) : ViewModel() {

    private var loginData: MutableLiveData<Users> = DatabaseClient.registerForLogin()
    private var usersData: MutableLiveData<List<Users>> = DatabaseClient.registerUsers()
    private var expenseData: MutableLiveData<List<Expense>> = DatabaseClient.registerExpenses()

    fun registerForLogin() = loginData

    fun registerForAllUsers() = usersData

    fun registerForExpenses() = expenseData

    fun isValidUser(email: String, password: String) {
        DatabaseClient.findValidUser(application.applicationContext, email, password)
    }

    fun getAllUsers() {
        DatabaseClient.getAppUsers(application.applicationContext)
    }

    fun insertExpense(expense: Expense) {
        DatabaseClient.addExpense(application.applicationContext, expense)
    }

    fun fetchExpenses() {
        DatabaseClient.fetchExpenses(application.applicationContext)
    }
}