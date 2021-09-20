package com.interview.splitexpense.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.interview.splitexpense.ExpenseConstants
import com.interview.splitexpense.R
import com.interview.splitexpense.adapter.ExpenseAdapter
import com.interview.splitexpense.db.DatabaseClient
import com.interview.splitexpense.interfaces.ExpenseCallback
import com.interview.splitexpense.model.Expense
import com.interview.splitexpense.viewmodel.DashboardViewModeFactory
import com.interview.splitexpense.viewmodel.DashboardViewModel
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList

class DashboardActivity : AppCompatActivity(), ExpenseCallback {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutNoRecords: LinearLayout
    private lateinit var fabAddButton: FloatingActionButton
    private lateinit var dashBoardViewModel: DashboardViewModel
    private var loggedInUser: String? = null
    val calendar = Calendar.getInstance()

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
                val adapter = ExpenseAdapter(it, this)
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
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.menu)
        }
        loggedInUser = DatabaseClient.getLoggedInUser(this)
        fabAddButton.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.layout_add_expense, null)
            val etExpeseTitle = view.findViewById<EditText>(R.id.et_expense_title)
            val etExpeseDescription = view.findViewById<EditText>(R.id.et_expense_description)
            val etExpeseAmount = view.findViewById<EditText>(R.id.et_expense_amount)
            val spExpesePaidBy = view.findViewById<Spinner>(R.id.spinner_paid_by)
            val etExpeseDate = view.findViewById<EditText>(R.id.et_expense_date)
            etExpeseDate.setOnClickListener {
                calendar.apply {
                    DatePickerDialog(this@DashboardActivity, { view, year, monthOfYear, dayOfMonth ->
                        etExpeseDate.setText("${dayOfMonth}/${monthOfYear+1}/$year")
                    }, get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_MONTH)).show()
                }
            }
            dashBoardViewModel.registerForAllUsers().value!!.map {
                if (it.name == loggedInUser)
                    "You" else it.name
            }.apply {
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
                val expensePaidBy = dashBoardViewModel.registerForAllUsers().value?.get(spExpesePaidBy.selectedItemPosition)?.name
                if (expenseTitle.isEmpty()) {
                    etExpeseTitle.error = getString(R.string.enter_input)
                    return@setOnClickListener
                }

                if (expenseDate.isEmpty()) {
                    etExpeseDate.error = getString(R.string.enter_input)
                    return@setOnClickListener
                }

                if (expenseAmount.isEmpty()) {
                    etExpeseAmount.error = getString(R.string.enter_input)
                    return@setOnClickListener
                }

                dashBoardViewModel.insertExpense(
                    Expense(
                        title = expenseTitle, date = expenseDate, amount = expenseAmount,
                        description = expenseDescription, paidBy = expensePaidBy
                    )
                )
                Handler().postDelayed({
                    dashBoardViewModel.fetchExpenses()
                }, 3000)
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    override fun expenseItemClicked(expense: Expense) {
        Intent(this, ExpenseDetailsActivity::class.java).apply {
            putExtra(ExpenseConstants.KEY_EXPENSE, expense)
            putStringArrayListExtra(
                ExpenseConstants.KEY_USERS,
                ArrayList(dashBoardViewModel.registerForAllUsers().value?.map {
                    if (it.name == loggedInUser)
                        "You" else it.name
                })
            )
            startActivity(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(ExpenseConstants.KEY_USER, loggedInUser)
        startActivity(intent)
        return true
    }
}