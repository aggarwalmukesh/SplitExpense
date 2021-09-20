package com.interview.splitexpense

import java.text.SimpleDateFormat

object ExpenseConstants {
    val KEY_EXPENSE: String = "expense"
    const val KEY_USERS = "users"
    const val KEY_USER = "user"
    const val KEY_PREFERENCES = "prefs"
    const val KEY_PREF_USER = "pref_user"
    const val LOGGED_OUT: String = "logged_out"
    val DATE_FORMAT_EXPENSE_LIST = SimpleDateFormat("MMM\ndd\nyyyy")
    val DATE_FORMAT_EXPENSE_DETAIL = SimpleDateFormat("MMM dd yyyy")
    val DATE_FORMAT_EXPENSE_ENTRY = SimpleDateFormat("dd/MM/yyyy")
}