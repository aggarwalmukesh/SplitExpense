package com.interview.splitexpense.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.interview.splitexpense.R
import com.interview.splitexpense.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialse()
        loginViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(LoginViewModel::class.java)

        loginViewModel.registerForLogin().observe(this, {
            if (it) {

            } else {
                Snackbar.make(btnLogin, getString(R.string.str_invalid_user),
                    Snackbar.LENGTH_INDEFINITE).show()
            }
        })
    }

    private fun initialse() {
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
    }
}