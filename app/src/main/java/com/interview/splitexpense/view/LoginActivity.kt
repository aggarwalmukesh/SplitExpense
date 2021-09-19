package com.interview.splitexpense

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.interview.splitexpense.view.DashboardActivity
import com.interview.splitexpense.viewmodel.LoginViewModeFactory
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
            it?.let {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra(ExpenseConstants.KEY_USER, it)
                startActivity(intent)
                finish()
            }
            if (it != null) {

            } else {
                Snackbar.make(
                    btnLogin, getString(R.string.str_invalid_user),
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
        })

        loginViewModel.checkIfUsersExist()
    }

    private fun initialse() {
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            loginViewModel.isValidUser(email, password)
        }
    }
}