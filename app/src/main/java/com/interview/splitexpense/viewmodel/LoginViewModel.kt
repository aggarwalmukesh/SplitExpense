package com.interview.splitexpense.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interview.splitexpense.db.DatabaseClient
import com.interview.splitexpense.model.Users

class LoginViewModel(val application: Application) : ViewModel() {

    val users = arrayOf(Users(name = "Anuradha", email = "Anuradha@gmail.com", password = "test"),
        Users(name = "Raju", email = "Raju@gmail.com", password = "test"),
        Users(name = "Baburao Apte", email = "Baburao@gmail.com", password = "test"),
        Users(name = "Ghanshyam", email = "Ghanshyam@gmail.com", password = "test"))

    private var loginData: MutableLiveData<Users> = DatabaseClient.registerForLogin()
    private var usersData: MutableLiveData<List<Users>> = DatabaseClient.registerUsers()

    fun registerForLogin() = loginData

    fun registerForAllUsers() = usersData

    fun isValidUser(email: String, password: String) {
        DatabaseClient.findValidUser(application.applicationContext, email, password)
    }

    fun getAllUsers() {
        DatabaseClient.getAppUsers(application.applicationContext)
    }

    fun insertAllUsers() {
        DatabaseClient.addUsers(application.applicationContext, users)
    }

    fun saveUser() {
        DatabaseClient.updateLoggedInUser(application, loginData.value?.name)
    }
}