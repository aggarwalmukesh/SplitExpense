package com.interview.splitexpense.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interview.splitexpense.db.DatabaseClient
import com.interview.splitexpense.model.Users

class LoginViewModel(val application: Application) : ViewModel() {

    val users = arrayOf(Users(name = "a", email = "a", password = "a"),
        Users(name = "b", email = "b", password = "b"),
        Users(name = "c", email = "c", password = "c"),
        Users(name = "d", email = "d", password = "d"))

    private var loginData: MutableLiveData<Users> = DatabaseClient.registerForLogin()
    private var usersData: MutableLiveData<List<Users>> = DatabaseClient.registerUsers()

    fun registerForLogin() = loginData

    fun registerForAllUsers() = usersData

    fun isValidUser(email: String, password: String) {
        DatabaseClient.findValidUser(application.applicationContext, email, password)
    }

    fun checkIfUsersExist() {
        DatabaseClient.getAppUsers(application.applicationContext)
    }

    fun insertAllUsers() {
        DatabaseClient.addUsers(application.applicationContext, users)
    }
}