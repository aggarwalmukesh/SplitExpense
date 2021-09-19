package com.interview.splitexpense.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interview.splitexpense.db.DatabaseClient

class LoginViewModel(val application: Application) : ViewModel() {
    private var loginData: MutableLiveData<Boolean> = DatabaseClient.registerForLogin()

    fun isValidUser(email: String, password: String) {
        DatabaseClient.isValidUser(application.applicationContext, email, password)
    }

    fun registerForLogin() = loginData
}