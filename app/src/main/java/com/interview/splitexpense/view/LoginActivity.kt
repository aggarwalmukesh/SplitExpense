package com.interview.splitexpense

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.interview.splitexpense.db.DatabaseClient
import com.interview.splitexpense.view.DashboardActivity
import com.interview.splitexpense.viewmodel.LoginViewModeFactory
import com.interview.splitexpense.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private var isLoggedOut = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialse()

        loginViewModel = LoginViewModeFactory(application)
            .create(LoginViewModel::class.java)

        // To get users list
        loginViewModel.registerForAllUsers().observe(this, {
            if (it.isNullOrEmpty()) {
                // if no users available in db, insert default users
                loginViewModel.insertAllUsers()
            }
        })

        loginViewModel.registerForLogin().observe(this, {
            // if user is validated, it will return user otherwise null
            if (isLoggedOut) {
                isLoggedOut = false
                return@observe
            }
            it?.let {
                loginViewModel.saveUser()
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
            Snackbar.make(
                btnLogin, getString(R.string.str_invalid_user),
                Snackbar.LENGTH_INDEFINITE
            ).show()
        })

        DatabaseClient.getLoggedInUser(this)?.apply {
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginViewModel.getAllUsers()
    }

    private fun initialse() {
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        isLoggedOut = intent.getBooleanExtra(ExpenseConstants.LOGGED_OUT, false)
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().lowercase()
            val password = etPassword.text.toString()
            loginViewModel.isValidUser(email, password)
        }
    }
}