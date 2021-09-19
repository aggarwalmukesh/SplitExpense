package com.interview.splitexpense.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Users(@PrimaryKey(autoGenerate = true) val uid: Int = 0,
            @ColumnInfo val name: String,
            @ColumnInfo  val email: String,
            @ColumnInfo val password: String)