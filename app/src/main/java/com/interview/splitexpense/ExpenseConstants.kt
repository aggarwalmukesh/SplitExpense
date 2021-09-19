package com.interview.splitexpense

import java.text.SimpleDateFormat

object ExpenseConstants {
    val KEY_EXPENSE: String = "expense"
    const val KEY_USERS = "users"
    const val KEY_USER = "user"
    const val KEY_PREFERENCES = "prefs"
    const val KEY_PREF_USER = "pref_user"
    val DATE_FORMAT_EXPENSE_LIST = SimpleDateFormat("MMM\ndd\nyyyy")
    val DATE_FORMAT_EXPENSE_DETAIL = SimpleDateFormat("MMM dd yyyy")
}