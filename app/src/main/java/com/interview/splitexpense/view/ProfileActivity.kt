package com.interview.splitexpense.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.interview.splitexpense.ExpenseConstants
import com.interview.splitexpense.LoginActivity
import com.interview.splitexpense.R
import com.interview.splitexpense.db.DatabaseClient

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        intent?.getStringExtra(ExpenseConstants.KEY_USER)?.let {
            val textLoggedIn = findViewById<TextView>(R.id.txt_looged_in)
            textLoggedIn.text = it
        }
    }

    fun closeProfile(view: View) {
        finish()
    }

    fun logout(view: View) {
        DatabaseClient.logoutUser(this)
        Intent(this, LoginActivity::class.java).apply {
            putExtra(ExpenseConstants.LOGGED_OUT, true)
            startActivity(this)
            finishAffinity()
        }
    }
}