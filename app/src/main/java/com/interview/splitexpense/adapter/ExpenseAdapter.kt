package com.interview.splitexpense.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interview.splitexpense.ExpenseConstants
import com.interview.splitexpense.R
import com.interview.splitexpense.adapter.ExpenseAdapter.ViewHolder
import com.interview.splitexpense.interfaces.ExpenseCallback
import com.interview.splitexpense.model.Expense
import java.util.*

class ExpenseAdapter(var expenseRecords: List<Expense>, val expenseCallback: ExpenseCallback)
    : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_expense, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindExpense(expenseRecords.get(position))
        holder.itemView.setOnClickListener {
            expenseCallback.expenseItemClicked(expenseRecords.get(position))
        }
    }

    override fun getItemCount(): Int {
        return expenseRecords.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expenseTitle = itemView.findViewById<TextView>(R.id.txt_expense_title)
        val expenseAmount = itemView.findViewById<TextView>(R.id.txt_expense_amount)
        val expensePaidBy = itemView.findViewById<TextView>(R.id.txt_expense_paid_by)
        val expenseDate = itemView.findViewById<TextView>(R.id.txt_date)

        fun bindExpense(expense: Expense) {
            expense.let {
                expenseTitle.text = it.title
                expenseAmount.text = "Total Amount: ${it.amount}"
                expensePaidBy.text = "Paid By: ${it.paidBy}"
                expenseDate.text = ExpenseConstants.DATE_FORMAT_EXPENSE_LIST.format(Date())
            }
        }
    }
}