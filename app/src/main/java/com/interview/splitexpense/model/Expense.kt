package com.interview.splitexpense.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Expense (@PrimaryKey(autoGenerate = true) val uid: Int = 0,
               @ColumnInfo val title: String?,
               @ColumnInfo val description: String?,
               @ColumnInfo val date: String?,
               @ColumnInfo val amount: String?,
               @ColumnInfo val paidBy: String?,
               @ColumnInfo val sharedBy: String? = "Everyone equally",
)