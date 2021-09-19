package com.interview.splitexpense.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.interview.splitexpense.R
import com.interview.splitexpense.adapter.ExpenseAdapter
import com.interview.splitexpense.model.Expense
import com.interview.splitexpense.viewmodel.DashboardViewModeFactory
import com.interview.splitexpense.viewmodel.DashboardViewModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutNoRecords: LinearLayout
    private lateinit var fabAddButton: FloatingActionButton
    private lateinit var dashBoardViewModel: DashboardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initialise()
        dashBoardViewModel = DashboardViewModeFactory(application)
            .create(DashboardViewModel::class.java)

        dashBoardViewModel.registerForExpenses().observe(this, {
            if (it.isNullOrEmpty()) {
                layoutNoRecords.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                layoutNoRecords.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager = LinearLayoutManager(this)
                val adapter = ExpenseAdapter(it)
                recyclerView.adapter = adapter
            }
        })
        dashBoardViewModel.getAllUsers()
        dashBoardViewModel.fetchExpenses()
    }

    private fun initialise() {
        recyclerView = findViewById(R.id.recycler_view)
        layoutNoRecords = findViewById(R.id.layout_no_records)
        fabAddButton = findViewById(R.id.fab_add)
        fabAddButton.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.layout_add_expense, null)
            val etExpeseTitle = view.findViewById<EditText>(R.id.et_expense_title)
            val etExpeseDescription = view.findViewById<EditText>(R.id.et_expense_description)
            val etExpeseAmount = view.findViewById<EditText>(R.id.et_expense_amount)
            val spExpesePaidBy = view.findViewById<Spinner>(R.id.spinner_paid_by)
            val etExpeseDate = view.findViewById<EditText>(R.id.et_expense_date)
            dashBoardViewModel.registerForAllUsers().value!!.map { it.name!! }.apply {
                spExpesePaidBy.adapter = ArrayAdapter(
                    this@DashboardActivity,
                    android.R.layout.simple_list_item_1, this
                )
            }

            dialog.setContentView(view)
            dialog.findViewById<Button>(R.id.btn_save_expense)?.setOnClickListener {
                val expenseTitle = etExpeseTitle.text.toString()
                val expenseDate = etExpeseDate.text.toString()
                val expenseAmount = etExpeseAmount.text.toString()
                val expenseDescription = etExpeseDescription.text.toString()
                val expensePaidBy = spExpesePaidBy.selectedItem.toString()
                dashBoardViewModel.insertExpense(
                    Expense(
                        title = expenseTitle, date = expenseDate, amount = expenseAmount,
                        description = expenseDescription, paidBy = expensePaidBy
                    )
                )
            }
            dialog.show()
        }
    }
}