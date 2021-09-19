package com.interview.splitexpense.interfaces

import com.interview.splitexpense.model.Expense

interface ExpenseCallback {
    fun expenseItemClicked(expense: Expense)
}