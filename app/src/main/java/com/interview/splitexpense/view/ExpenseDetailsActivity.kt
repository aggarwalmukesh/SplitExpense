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
        val labelAmountOwe = findViewById<TextView>(R.id.label_amount_owe)
        val expenseItem = intent.getParcelableExtra<Expense>(ExpenseConstants.KEY_EXPENSE)
        val usersList = intent.getStringArrayListExtra(ExpenseConstants.KEY_USERS)

        expenseItem?.apply {
            txtExpensetitle.text = title
            txtExpenseDate.text = ExpenseConstants.DATE_FORMAT_EXPENSE_DETAIL.format(
                ExpenseConstants.DATE_FORMAT_EXPENSE_ENTRY.parse(date))
            txtExpenseAmount.text = "$amount INR"
            txtExpenseDescription.text = description
            if (DatabaseClient.getLoggedInUser(this@ExpenseDetailsActivity) == paidBy) {
                labelAmountOwe.text = "You get back:"
                txtExpensePaidBy.text = "You"
                txtExpenseOwe.text = "${amount?.toInt()?.times(3)?.div(4)} INR"
            } else {
                labelAmountOwe.text = "You owe:"
                txtExpensePaidBy.text = paidBy
                txtExpenseOwe.text = "${amount?.toInt()?.div(4)} INR"
            }
            txtExpenseSplitWith.text = usersList.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}