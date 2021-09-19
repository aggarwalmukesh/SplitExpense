package com.interview.splitexpense.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.interview.splitexpense.ExpenseConstants
import com.interview.splitexpense.R
import com.interview.splitexpense.db.DatabaseClient
import com.interview.splitexpense.model.Expense
import java.util.*

class ExpenseDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val txtExpensetitle = findViewById<TextView>(R.id.txt_expense_title)
        val txtExpenseDate = findViewById<TextView>(R.id.txt_expense_date)
        val txtExpenseAmount = findViewById<TextView>(R.id.txt_expense_amount)
        val txtExpensePaidBy = findViewById<TextView>(R.id.txt_expense_paid_by)
        val txtExpenseDescription = findViewById<TextView>(R.id.txt_expense_description)
        val txtExpenseSplitWith = findViewById<TextView>(R.id.txt_expense_split_with)
        val txtExpenseOwe = findViewById<TextView>(R.id.txt_expense_owe)
        val expenseItem = intent.getParcelableExtra<Expense>(ExpenseConstants.KEY_EXPENSE)
        val usersList = intent.getStringArrayListExtra(ExpenseConstants.KEY_USERS)

        expenseItem?.apply {
            txtExpensetitle.text = title
            txtExpenseDate.text = ExpenseConstants.DATE_FORMAT_EXPENSE_DETAIL.format(Date())
            txtExpenseAmount.text = "$amount INR"
            txtExpenseDescription.text = description
            txtExpensePaidBy.text = if (DatabaseClient
                .getLoggedInUser(this@ExpenseDetailsActivity) == paidBy) "You" else paidBy

            txtExpenseSplitWith.text = usersList.toString()
            txtExpenseOwe.text = "$amount INR"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}